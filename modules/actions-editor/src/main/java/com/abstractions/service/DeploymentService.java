package com.abstractions.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.abstractions.model.ProfilingInfo;
import com.abstractions.model.ProfilingInfoJSONMarshaller;
import com.abstractions.model.Server;
import com.abstractions.model.ServerCommand;
import com.abstractions.model.UserImpl;
import com.abstractions.repository.GenericRepository;
import com.abstractions.utils.MessageUtils;

@Service
public class DeploymentService {

	private static final Log log = LogFactory.getLog(DeploymentService.class);
	
	private GenericRepository repository;
	private SnapshotService snapshotService;
	private UserService userService;
	private ServerService serverService;
	private CloudFrontService cfService;
	
	public DeploymentService() { }

	public DeploymentService(
			GenericRepository repository, 
			SnapshotService snapshotService, 
			UserService userService, 
			ServerService serverService,
			CloudFrontService cfService) {

	  Validate.notNull(repository);
		Validate.notNull(snapshotService);
		Validate.notNull(userService);
		Validate.notNull(serverService);
    Validate.notNull(cfService);

		this.repository = repository;
		this.snapshotService = snapshotService;
		this.userService = userService;
		this.serverService = serverService;
    this.cfService = cfService;
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

    if (this.isCDNAware(applicationSnapshot)) {
      this.cfService.distributeResources(applicationSnapshot);
    }
	  
		UserImpl user = (UserImpl) this.userService.getCurrentUser();
		final Deployment deployment = new Deployment(applicationSnapshot, user);

		for (long serverId : servers) {
			deployment.addServer(this.serverService.getServer(serverId));
		}

		this.repository.save(deployment);
	}

	private boolean isCDNAware(ApplicationSnapshot applicationSnapshot) {
			return 
			    applicationSnapshot.getProperty(MessageUtils.APPLICATION_CLOUDFRONT_ACCESS) != null &&
			    applicationSnapshot.getProperty(MessageUtils.APPLICATION_CLOUDFRONT_SECRET) != null &&
					!applicationSnapshot.getResources().isEmpty();
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
	public void addLazyComputedCache(
			long deploymentId, 
			String contextId, 
			String elementId,
			String memcachedURL,
			String ttl,
			String keyExpression,
			String cacheExpressions) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		
		for (DeploymentToServer toServer : deployment.getToServers()) {
			ServerCommand command = new ServerCommand("ADD_LAZY_COMPUTED_CACHE", toServer);
			command.addArgument("applicationId", Long.valueOf(deployment.getSnapshot().getApplication().getId()).toString());
			command.addArgument("deploymentId", Long.valueOf(deploymentId).toString());
			command.addArgument("contextId", contextId);
			command.addArgument("elementId", elementId);

			command.addArgument("memcachedURL", memcachedURL);
			command.addArgument("ttl", ttl);
			command.addArgument("keyExpression", keyExpression);
			command.addArgument("cacheExpressions", cacheExpressions);

			this.repository.save(command);
		}
	}

	@Transactional
	public void addLazyAutorefreshableCache(
			long deploymentId, 
			String contextId, 
			String elementId,
			String memcachedURL,
			String oldCacheEntryInMills,
			String keyExpression,
			String cacheExpressions) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);

		for (DeploymentToServer toServer : deployment.getToServers()) {
			ServerCommand command = new ServerCommand("ADD_LAZY_AUTOREFRESHABLE_CACHE", toServer);
			command.addArgument("applicationId", Long.valueOf(deployment.getSnapshot().getApplication().getId()).toString());
			command.addArgument("deploymentId", Long.valueOf(deploymentId).toString());
			command.addArgument("contextId", contextId);
			command.addArgument("elementId", elementId);

			command.addArgument("memcachedURL", memcachedURL);
			command.addArgument("oldCacheEntryInMills", oldCacheEntryInMills);
			command.addArgument("keyExpression", keyExpression);
			command.addArgument("cacheExpressions", cacheExpressions);

			this.repository.save(command);
		}
	}
	
	@Transactional
	public void addProfiler(long deploymentId, String contextId, String elementId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		
		for (DeploymentToServer toServer : deployment.getToServers()) {
			ServerCommand command = new ServerCommand("ADD_PROFILER", toServer);
			command.addArgument("applicationId", Long.valueOf(deployment.getSnapshot().getApplication().getId()).toString());
			command.addArgument("deploymentId", Long.valueOf(deploymentId).toString());
			command.addArgument("contextId", contextId);
			command.addArgument("elementId", elementId);
			
			this.repository.save(command);
		}
	}

	@Transactional
	public void removeProfiler(long deploymentId, String contextId, String elementId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);

		for (DeploymentToServer toServer : deployment.getToServers()) {
			ServerCommand command = new ServerCommand("REMOVE_PROFILER", toServer);
			command.addArgument("applicationId", Long.valueOf(deployment.getSnapshot().getApplication().getId()).toString());
			command.addArgument("deploymentId", Long.valueOf(deploymentId).toString());
			command.addArgument("contextId", contextId);
			command.addArgument("elementId", elementId);
			
			this.repository.save(command);
		}
	}
	
	@Transactional
	public JSONObject getProfilingInfo(long deploymentId, String contextId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		//TODO add deployment id to the search
		
		String applicationId = Long.valueOf(deployment.getSnapshot().getApplication().getId()).toString();
		List<Object[]> datas = this.repository.getLastProfilingOf(applicationId);
		ProfilingInfo info = new ProfilingInfo(applicationId);

		for (Object[] data : datas) {
			String elementId = (String) data[0];
			Double average = (Double) data[1];
			
			info.addAverage(elementId, average);
		}
		
		
		JSONArray partialResults = new JSONArray();
		JSONObject result = new JSONObject();

		try {
			JSONObject profilingInfo = new JSONObject();
			profilingInfo.put("profilingInfo", ProfilingInfoJSONMarshaller.toJSON(info));
			partialResults.put(profilingInfo);
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

		for (DeploymentToServer toServer : deployment.getToServers()) {
			ServerCommand command = new ServerCommand("ADD_LOGGER", toServer);
			command.addArgument("applicationId", Long.valueOf(deployment.getSnapshot().getApplication().getId()).toString());
			command.addArgument("deploymentId", Long.valueOf(deploymentId).toString());
			command.addArgument("contextId", contextId);
			command.addArgument("elementId", elementId);

			command.addArgument("beforeExpression", beforeExpression);
			command.addArgument("afterExpression", afterExpression);
			command.addArgument("isBeforeConditional", isBeforeConditional.toString());
			command.addArgument("isAfterConditional", isAfterConditional.toString());
			command.addArgument("beforeConditionalExpressionValue", beforeConditionalExpressionValue);
			command.addArgument("afterConditionalExpressionValue", afterConditionalExpressionValue);

			this.repository.save(command);
		}
	}

	@Transactional
	public void removeLogger(long deploymentId, String contextId, String elementId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		
		for (DeploymentToServer toServer : deployment.getToServers()) {
			ServerCommand command = new ServerCommand("REMOVE_LOGGER", toServer);
			command.addArgument("applicationId", Long.valueOf(deployment.getSnapshot().getApplication().getId()).toString());
			command.addArgument("deploymentId", Long.valueOf(deploymentId).toString());
			command.addArgument("contextId", contextId);
			command.addArgument("elementId", elementId);

			this.repository.save(command);
		}		
	}

	@Transactional
	public JSONObject getLogger(long deploymentId, String contextId, String elementId) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		String applicationId = Long.valueOf(deployment.getSnapshot().getApplication().getId()).toString();

		JSONArray partialResults = new JSONArray();
		List<String> datas = this.repository.getLastLoggingInfoOf(applicationId, elementId);

		for (String data : datas) {
			String string = StringUtils.defaultString(data);
			partialResults.put(string);
		}
		
		JSONObject result = new JSONObject();
		try {
			result.put("lines", partialResults);
		} catch (JSONException e) {
			log.warn("Error writing json", e);
		}
		
		return result;
	}
	
	@Transactional
	public InputStream startDeployment(long deploymentId, String serverId, String serverKey) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		Server server = this.serverService.getServer(serverId, serverKey);
		
		if (server != null) {
			DeploymentToServer toServer = deployment.getToServer(server.getId());
			toServer.setState(DeploymentState.STARTED);
			this.repository.save(toServer);
			InputStream io = null;
      try {
        io = this.snapshotService.getZipFor(deployment.getSnapshot().getId());
      } catch (IOException e) {
        log.error("Error when obtaining snapshot");
      }
			return io;
		} else {
			return null;
		}
	}
	
	@Transactional
	public void endDeploymentWithErrors(long deploymentId, String serverId, String serverKey) {
		this.setStateToServer(deploymentId, serverId, serverKey, DeploymentState.FINISH_WITH_ERRORS);
	}

	@Transactional
	public void endDeploymentSuccessfully(long deploymentId, String serverId, String serverKey) {
		this.setStateToServer(deploymentId, serverId, serverKey, DeploymentState.FINISH_SUCCESSFULLY);
	}

	private void setStateToServer(long deploymentId, String serverId, String serverKey, DeploymentState state) {
		Deployment deployment = this.repository.get(Deployment.class, deploymentId);
		Server server = this.serverService.getServer(serverId, serverKey);
		
		if (server != null) {
			DeploymentToServer toServer = deployment.getToServer(server.getId());
			toServer.setState(state);
			this.repository.save(toServer);		
		}
	}
	
	@Transactional
	public List<Object[]> getPendingDeploymentIdsFor(long serverId) {
		return this.repository.getPendingDeploymentIdsFor(serverId);
	}
}
