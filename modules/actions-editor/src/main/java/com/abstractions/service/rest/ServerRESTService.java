package com.abstractions.service.rest;

import java.io.File;
import java.util.AbstractMap.SimpleEntry;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.abstractions.model.LoggingInfo;
import com.abstractions.model.LoggingInfoJSONMarshaller;
import com.abstractions.model.ProfilingInfo;
import com.abstractions.model.ProfilingInfoJSONMarshaller;
import com.abstractions.model.Server;
import com.abstractions.model.ServerCommand;
import com.abstractions.service.DeploymentService;
import com.abstractions.service.ServerService;

@Path("/server")
public class ServerRESTService {

	private static final Log log = LogFactory.getLog(ServerRESTService.class);
	
	private ServerService service;
	private DeploymentService deploymentService;
	
	public ServerRESTService(ServerService service, DeploymentService deploymentService) {
		this.service = service;
		this.deploymentService = deploymentService;
	}

	@POST
	@Path("/profiling")
	public Response receiveProfiling(
			@FormParam("server-id") String serverId, 
			@FormParam("server-key") String serverKey,
			@FormParam("profiling-info") String profilingInfoJSON) {
		
		Server server = this.service.getServer(serverId, serverKey);
		
		if (server != null) {
			try {
				JSONObject root = new JSONObject(profilingInfoJSON);
				JSONArray profilings = root.getJSONArray("profiling");
				
				for (int i = 0; i < profilings.length(); i++) {
					JSONObject applicationProfiling = profilings.getJSONObject(i);
					
					ProfilingInfo info = ProfilingInfoJSONMarshaller.fromJSON(applicationProfiling);
					
					this.service.addProfilingInfo(server, info);
				}
			} catch (JSONException e) {
				log.warn("Error parsing json of server id " + StringUtils.defaultString(serverId));
			}
		} else {
			log.warn("No server found to store profiling of " + StringUtils.defaultString(serverId));
		}
		
		return ResponseUtils.ok();
	}
	
	@POST
	@Path("/logs")
	public Response receiveLogs(
			@FormParam("server-id") String serverId, 
			@FormParam("server-key") String serverKey,
			@FormParam("logs") String logsJSON) {
		
		Server server = this.service.getServer(serverId, serverKey);
		
		if (server != null) {
			try {
				JSONObject root = new JSONObject(logsJSON);
				JSONArray logs = root.getJSONArray("logs");
				
				for (int i = 0; i < logs.length(); i++) {
					JSONObject applicationLogs = logs.getJSONObject(i);
					
					LoggingInfo info = LoggingInfoJSONMarshaller.fromJSON(applicationLogs);
					
					this.service.addLoggingInfo(server, info);
				}
			} catch (JSONException e) {
				log.warn("Error parsing json of server id " + StringUtils.defaultString(serverId));
			}
		} else {
			log.warn("No server found to store logging of " + StringUtils.defaultString(serverId));
		}
		
		return ResponseUtils.ok();
	}

	@POST
	@Path("/status")
	public Response ping(@FormParam("server-id") String serverId, @FormParam("server-key") String serverKey) {
		this.service.updateServerStatusWithKey(serverId, serverKey);
		Server server = this.service.getServer(serverId, serverKey);
		JSONArray deploymentsArray = new JSONArray();
		JSONArray commandsArray = new JSONArray();

		if (server != null) {
			List<Object[]> pendingDeployments = this.deploymentService.getPendingDeploymentIdsFor(server.getId());
			
			for (Object[] data : pendingDeployments) {
				JSONObject deploymentJSON = new JSONObject();
				try {
					deploymentJSON.put("deploymentId", data[0]);
					deploymentJSON.put("applicationId", data[1]);
					deploymentsArray.put(deploymentJSON);
				} catch (JSONException e) {
					return ResponseUtils.fail("Error writing json");
				}
			}
			
			List<ServerCommand> commands = this.service.getPendingCommands(serverId, serverKey);
			
			for (ServerCommand command : commands) {
				JSONObject commandJSON = new JSONObject();
				JSONObject argsJSON = new JSONObject();
				try {
					commandJSON.put("id", command.getId());
					commandJSON.put("name", command.getName());
					for (Map.Entry<String, String> entry : command.getArguments().entrySet()) {
						argsJSON.put(entry.getKey(), entry.getValue());
					}
					commandJSON.put("args", argsJSON);
					commandsArray.put(commandJSON);
				} catch (JSONException e) {
					return ResponseUtils.fail("Error writing json");
				}
			}
		}
		
		return ResponseUtils
				.okJsons(new SimpleEntry("deployments", deploymentsArray),
						new SimpleEntry("commands", commandsArray));
	}

	@POST
	@Path("/statistics")
	public Response getStatistics(
			@FormParam("server-id") String serverId, 
			@FormParam("server-key") String serverKey,
			@FormParam("statistics") String statisticsAsJSON) {
		
		try {
			JSONObject root = new JSONObject(statisticsAsJSON);
			for (Iterator iterator = root.keys(); iterator.hasNext();) {
				String contextId = (String) iterator.next();
				
				JSONObject jsonStats = root.getJSONObject(contextId);
				Date date = new Date(jsonStats.getLong("date"));
				long uncaughtExceptions = jsonStats.getLong("uncaughtExceptions");
				
				Map<String, Long> receivedMessages = new HashMap<String, Long>();
				JSONObject receivedMessagesJSON = jsonStats.getJSONObject("receivedMessages");
				for (Iterator iterator2 = receivedMessagesJSON.keys(); iterator2.hasNext();) {
					String messageSourceId = (String) iterator2.next();
					long messagesReceived = receivedMessagesJSON.getLong(messageSourceId);
					receivedMessages.put(messageSourceId, messagesReceived);
				}

				this.service.updateStatistics(serverId, serverKey, contextId, date, uncaughtExceptions, receivedMessages);
			}
			
			return ResponseUtils
					.ok();

		} catch (JSONException e) {
			return ResponseUtils.fail("Error parsing json");
		}
	}
	
	@POST
	@Path("/deployment/{deploymentId}/start")
	public Response startDeployment(@PathParam("deploymentId") long deploymentId, @FormParam("server-id") String serverId, @FormParam("server-key") String serverKey) {
		String filename = this.deploymentService.startDeployment(deploymentId, serverId, serverKey);
		if (filename != null) {
			File fileToSend = new File(filename);
			return Response
					.ok(fileToSend, "application/zip")
					.build();
		} else {
			return Response.serverError().build();
		}
	}

	@POST
	@Path("/deployment/{deploymentId}/end-success")
	public Response endDeploymentSuccess(@PathParam("deploymentId") long deploymentId, @FormParam("server-id") String serverId, @FormParam("server-key") String serverKey) {
		this.deploymentService.endDeploymentSuccessfully(deploymentId, serverId, serverKey);
		return Response
				.ok()
				.build();
	}

	@POST
	@Path("/deployment/{deploymentId}/end-failure")
	public Response endDeploymentErrors(@PathParam("deploymentId") long deploymentId, @FormParam("server-id") String serverId, @FormParam("server-key") String serverKey) {
		this.deploymentService.endDeploymentWithErrors(deploymentId, serverId, serverKey);
		return Response
				.ok()
				.build();
	}
	
	
	@POST
	@Path("/commands")
	public Response receiveCommandStatus(
			@FormParam("server-id") String serverId, 
			@FormParam("server-key") String serverKey,
			@FormParam("success-ids") String successIdsCSV,
			@FormParam("failed-ids") String failedIdsCSV) {
		
		successIdsCSV = StringUtils.defaultString(successIdsCSV);
		failedIdsCSV = StringUtils.defaultString(failedIdsCSV);
		
		Set<Long> successIds = new HashSet<Long>();
		Set<Long> failedIds = new HashSet<Long>();
		
		for (String id : StringUtils.split(successIdsCSV, ',')) {
			try {
				successIds.add(Long.valueOf(id));
			} catch (Exception e) {
				log.warn("Error parsing id " + StringUtils.defaultString(id), e);
			}
		}
		
		for (String id : StringUtils.split(failedIdsCSV, ',')) {
			try {
				failedIds.add(Long.valueOf(id));
			} catch (Exception e) {
				log.warn("Error parsing id " + StringUtils.defaultString(id), e);
			}
		}
		
		this.service.updateCommandStatus(serverId, serverKey, successIds, failedIds);

		return ResponseUtils.ok();
	}
}
