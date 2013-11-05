package com.abstractions.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.IOUtils;
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
import com.abstractions.model.DeploymentToServer;
import com.abstractions.model.Flow;
import com.abstractions.model.Server;
import com.abstractions.model.User;
import com.abstractions.repository.GenericRepository;
import com.abstractions.service.core.ResourceService;

@Service
public class DeploymentService {

	private static final Log log = LogFactory.getLog(DeploymentService.class);
	
	private HttpClient client;

	private GenericRepository repository;
	private SnapshotService snapshotService;
	private UserService userService;
	private ServerService serverService;
	private ResourceService fileService;
	
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
			ResourceService fileService) {
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
	public void addCache(long deploymentId, String contextId, String elementId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		long applicationId = deployment.getSnapshot().getApplication().getId();
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + applicationId + "/cache/" + elementId;
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
		long applicationId = deployment.getSnapshot().getApplication().getId();
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + applicationId + "/profile/" + elementId;
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
		long applicationId = deployment.getSnapshot().getApplication().getId();
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + applicationId + "/profile/" + elementId;
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
		long applicationId = deployment.getSnapshot().getApplication().getId();
		
		JSONArray partialResults = new JSONArray();
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + applicationId + "/profilingInfo";
			
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
			String afterExpression, 
			Boolean isBeforeConditional, 
			Boolean isAfterConditional, 
			String beforeConditionalExpressionValue, 
			String afterConditionalExpressionValue) {
		
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		long applicationId = deployment.getSnapshot().getApplication().getId();

		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + applicationId + "/log/" + elementId;
			HttpPost method = new HttpPost(url);
			
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("beforeExpression", beforeExpression));
			urlParameters.add(new BasicNameValuePair("afterExpression", afterExpression));
			urlParameters.add(new BasicNameValuePair("isBeforeConditional", isBeforeConditional.toString()));
			urlParameters.add(new BasicNameValuePair("isAfterConditional", isAfterConditional.toString()));
			urlParameters.add(new BasicNameValuePair("beforeConditionalExpressionValue", beforeConditionalExpressionValue));
			urlParameters.add(new BasicNameValuePair("afterConditionalExpressionValue", afterConditionalExpressionValue));

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
		long applicationId = deployment.getSnapshot().getApplication().getId();
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + applicationId + "/log/" + elementId;
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
		long applicationId = deployment.getSnapshot().getApplication().getId();

		JSONArray partialResults = new JSONArray();
		
		for (Server server : deployment.getServers()) {
			String url = "http://" + server.getIpDNS() + ":" + server.getPort()
					+ "/service/server/" + applicationId + "/log/" + elementId;
			
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

	@Transactional
	public String startDeployment(long deploymentId, String serverKey) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		Server server = this.serverService.getServer(serverKey);
		
		DeploymentToServer toServer = deployment.getToServer(server.getId());
		toServer.setState(DeploymentState.STARTED);
		this.repository.save(toServer);
		
		String filename = this.fileService.buildSnapshotPath(
				deployment.getSnapshot().getApplication().getId(), 
				deployment.getSnapshot().getId());
		return filename;
	}
	
	@Transactional
	public void endDeploymentWithErrors(long deploymentId, String serverKey) {
		this.setStateToServer(deploymentId, serverKey, DeploymentState.FINISH_WITH_ERRORS);
	}

	@Transactional
	public void endDeploymentSuccessfully(long deploymentId, String serverKey) {
		this.setStateToServer(deploymentId, serverKey, DeploymentState.FINISH_SUCCESSFULLY);
	}

	private void setStateToServer(long deploymentId, String serverKey, DeploymentState state) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		Server server = this.serverService.getServer(serverKey);
		
		DeploymentToServer toServer = deployment.getToServer(server.getId());
		toServer.setState(state);
		this.repository.save(toServer);		
	}
	
	@Transactional
	public List<Object[]> getPendingDeploymentIdsFor(long serverId) {
		return this.repository.getPendingDeploymentIdsFor(serverId);
	}
}
