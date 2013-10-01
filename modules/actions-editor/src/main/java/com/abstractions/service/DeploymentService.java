package com.abstractions.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.ApplicationSnapshot;
import com.abstractions.model.Deployment;
import com.abstractions.model.DeploymentState;
import com.abstractions.model.Flow;
import com.abstractions.model.Server;
import com.abstractions.model.User;
import com.abstractions.repository.GenericRepository;

@Service
public class DeploymentService {

	private static final Log log = LogFactory.getLog(DeploymentService.class);
	
	private HttpClient client;

	private GenericRepository repository;
	private SnapshotService snapshotService;
	private UserService userService;
	private ServerService serverService;
	private AsyncDeployer deployer;
	private FileService fileService;
	
	public DeploymentService() {
		ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager();
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		HttpConnectionParams.setSoTimeout(params, 3000);

		connManager.setMaxTotal(100);
		connManager.setDefaultMaxPerRoute(10);

		this.client = new DefaultHttpClient(connManager, params);
		this.client.getParams().setParameter(
				ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	}

	public DeploymentService(
			GenericRepository repository, 
			SnapshotService snapshotService, 
			UserService userService, 
			ServerService serverService,
			FileService fileService) {
		this();
		Validate.notNull(repository);
		Validate.notNull(snapshotService);
		Validate.notNull(userService);
		Validate.notNull(serverService);
		Validate.notNull(fileService);

		this.repository = repository;
		this.snapshotService = snapshotService;
		this.userService = userService;
		this.serverService = serverService;
		this.fileService = fileService;
	}

	@Transactional
	public Flow getFirstFlowOf(long deploymentId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		List<Flow> flows = deployment.getSnapshot().getFlows();
		return flows.isEmpty() ? null : flows.get(0);
	}
	
	@Transactional
	public void addDeployment(long snapshotId, long userId, Collection<Long> servers) {
		ApplicationSnapshot applicationSnapshot = this.snapshotService.getSnapshot(snapshotId);
		User user = this.userService.getUser(userId);
		final Deployment deployment = new Deployment(applicationSnapshot, user);

		for (long serverId : servers) {
			deployment.addServer(this.serverService.getServer(serverId));
		}

		this.repository.save(deployment);
		this.deployer.deploy(deployment.getId());
	}

	@Transactional
	public List<Deployment> getDeployments(long applicationSnapshotId) {
		List<Deployment> deployments = this.repository.get(Deployment.class, "snapshot", applicationSnapshotId);
		for (Deployment deployment : deployments) {
			deployment.getServerList();
		}
		return deployments;
	}

	@Transactional
	public void deploy(long deploymentId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		try {
			boolean deployed = true;
			for (Server server : deployment.getServers()) {
				deployed = deployed && this.basicDeploy(deployment, server);
			}
			
			if (deployed) {
				deployment.setState(DeploymentState.FINISH_SUCCESSFULLY);
			} else {
				deployment.setState(DeploymentState.FINISH_WITH_ERRORS);
			}
		} catch (Exception e) {
			deployment.setState(DeploymentState.FINISH_WITH_ERRORS);
		}
		
		this.repository.save(deployment);
	}

	private boolean basicDeploy(Deployment deployment, Server server) {
		String applicationId = Long.valueOf(deployment.getSnapshot().getApplication().getId()).toString();
		String snapshotId = Long.valueOf(deployment.getSnapshot().getId()).toString();
		
		InputStream snapshotFileIS = this.fileService.getContentsOfSnapshot(applicationId, snapshotId);

		if (snapshotFileIS == null) {
			return false;
		}
		
		String url = "http://" + server.getIpDNS() + ":" + server.getPort()
				+ "/service/server/" + applicationId + "/start";
		
		HttpPost post = new HttpPost(url);
		InputStream is = null;
		
		try {
			MultipartEntity entity = new MultipartEntity();
			entity.addPart("applicationZip", new InputStreamBody(snapshotFileIS, "application"));
			post.setEntity(entity);
			
			HttpResponse response = this.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();
			
			if (statusCode >= 200 && statusCode < 300) {
				is = response.getEntity().getContent();
				JSONObject json = new JSONObject(IOUtils.toString(is));
				return json.has("result") 
						&& StringUtils.equalsIgnoreCase(json.getString("result"), "success");
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("Error deploying context", e);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return false;		
	}

	@Transactional
	public void addCache(long deploymentId, String contextId, String elementId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + contextId + "/cache/" + elementId;
			HttpPost method = new HttpPost(url);
			
			try {
				List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
				//TODO set the parameters
				urlParameters.add(new BasicNameValuePair("memcachedURL", "127.0.0.1:11211"));
				urlParameters.add(new BasicNameValuePair("keyExpression", "message.properties['actions.http.productId']"));
				urlParameters.add(new BasicNameValuePair("cacheExpressions", "message.payload"));
				urlParameters.add(new BasicNameValuePair("ttl", "30"));
				method.setEntity(new UrlEncodedFormEntity(urlParameters));

				this.execute(method);
			} catch (Exception e) {
				log.warn("Error removing profiler", e);
			}
		}
	}
	
	@Transactional
	public void addProfiler(long deploymentId, String contextId, String elementId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + contextId + "/profile/" + elementId;
			HttpPut method = new HttpPut(url);
			try {
				this.execute(method);
			} catch (Exception e) {
				log.warn("Error removing profiler", e);
			}
		}
	}

	@Transactional
	public void removeProfiler(long deploymentId, String contextId, String elementId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + contextId + "/profile/" + elementId;
			HttpDelete method = new HttpDelete(url);
			try {
				this.execute(method);
			} catch (Exception e) {
				log.warn("Error removing profiler", e);
			}
		}
	}
	
	@Transactional
	public JSONObject getProfilingInfo(long deploymentId, String contextId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		
		JSONArray partialResults = new JSONArray();
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + contextId + "/profilingInfo";
			
			HttpGet method = new HttpGet(url);
			InputStream is = null;
			try {
				HttpResponse response = this.execute(method);
				int statusCode = response.getStatusLine().getStatusCode();
				is = response.getEntity().getContent();
				if (statusCode >= 200 && statusCode < 300) {
					JSONObject json = new JSONObject(IOUtils.toString(is));
					partialResults.put(json);
				}
			} catch (Exception e) {
				log.warn("Error getting profiler", e);
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		
		JSONObject result = new JSONObject();
		try {
			result.put("servers", partialResults);
		} catch (JSONException e) {
			log.warn("Error writing json", e);
		}
		
		return result;
	}
	
	@Transactional
	public void addLogger(
			long deploymentId, 
			String contextId,
			String elementId, 
			String beforeExpression, 
			String afterExpression) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);

		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + contextId + "/log/" + elementId;
			HttpPost method = new HttpPost(url);
			
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("beforeExpression", beforeExpression));
			urlParameters.add(new BasicNameValuePair("afterExpression", afterExpression));

			try {
				method.setEntity(new UrlEncodedFormEntity(urlParameters));
				this.execute(method);
			} catch (Exception e) {
				log.warn("Error adding logger", e);
			}
		}
	}

	@Transactional
	public void removeLogger(long deploymentId, String contextId, String elementId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + contextId + "/log/" + elementId;
			HttpDelete method = new HttpDelete(url);
			try {
				this.execute(method);
			} catch (Exception e) {
				log.warn("Error removing logger", e);
			}
		}		
	}

	@Transactional
	public JSONObject getLogger(long deploymentId, String contextId, String elementId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		
		JSONArray partialResults = new JSONArray();
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + contextId + "/log/" + elementId;
			
			HttpGet method = new HttpGet(url);
			InputStream is = null;
			try {
				HttpResponse response = this.execute(method);
				int statusCode = response.getStatusLine().getStatusCode();
				is = response.getEntity().getContent();
				if (statusCode >= 200 && statusCode < 300) {
					JSONObject json = new JSONObject(IOUtils.toString(is));
					partialResults.put(json);
				}
			} catch (Exception e) {
				log.warn("Error getting logger", e);
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		
		JSONObject result = new JSONObject();
		try {
			result.put("servers", partialResults);
		} catch (JSONException e) {
			log.warn("Error writing json", e);
		}
		
		return result;
	}
	
	private HttpResponse execute(HttpUriRequest method) throws ClientProtocolException, IOException {
		return this.client.execute(method);
	}

	public AsyncDeployer getDeployer() {
		return deployer;
	}

	public void setDeployer(AsyncDeployer deployer) {
		this.deployer = deployer;
	}
}
