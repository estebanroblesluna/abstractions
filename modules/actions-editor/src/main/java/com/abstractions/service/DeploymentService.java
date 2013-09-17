package com.abstractions.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.ApplicationSnapshot;
import com.abstractions.model.Deployment;
import com.abstractions.model.DeploymentState;
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
			ServerService serverService) {
		this();
		Validate.notNull(repository);
		Validate.notNull(snapshotService);
		Validate.notNull(userService);
		Validate.notNull(serverService);

		this.repository = repository;
		this.snapshotService = snapshotService;
		this.userService = userService;
		this.serverService = serverService;
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
		return this.repository.get(Deployment.class, "snapshot", applicationSnapshotId);
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
		if (deployment.getSnapshot().getFlows().isEmpty()) {
			return false;
		}
		
		String contextJSON = deployment.getSnapshot().getFlows().get(0).getJson();
		String url = "http://" + server.getIpDNS() + ":" + server.getPort()
				+ "/service/server/start";

		HttpPost post = new HttpPost(url);
		try {
			
			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("contextDefinition", contextJSON));
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			
			HttpResponse response = this.client.execute(post);
			int statusCode = response.getStatusLine().getStatusCode();

			if (statusCode >= 200 && statusCode < 300) {
				InputStream is = response.getEntity().getContent();
				JSONObject json = new JSONObject(IOUtils.toString(is));
				return json.has("result") 
						&& StringUtils.equalsIgnoreCase(json.getString("result"), "success");
			} else {
				return false;
			}
		} catch (Exception e) {
			log.error("Error deploying context", e);
		}
		return false;		
	}

	public AsyncDeployer getDeployer() {
		return deployer;
	}

	public void setDeployer(AsyncDeployer deployer) {
		this.deployer = deployer;
	}
}
