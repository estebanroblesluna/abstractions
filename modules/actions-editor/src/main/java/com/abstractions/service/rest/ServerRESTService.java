package com.abstractions.service.rest;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.abstractions.model.Server;
import com.abstractions.service.DeploymentService;
import com.abstractions.service.ServerService;

@Path("/server")
public class ServerRESTService {

	private ServerService service;
	private DeploymentService deploymentService;
	
	public ServerRESTService(ServerService service, DeploymentService deploymentService) {
		this.service = service;
		this.deploymentService = deploymentService;
	}
	
	@POST
	@Path("/status")
	public Response ping(@FormParam("server-key") String serverKey) {
		this.service.updateServerStatusWithKey(serverKey);
		Server server = this.service.getServer(serverKey);
		List<Long> pendingDeployments = this.deploymentService.getPendingDeploymentIdsFor(server.getId());
		
		JSONArray array = new JSONArray();
		for (Long deploymentId : pendingDeployments) {
			array.put(deploymentId);
		}
		
		return ResponseUtils
				.ok("deploymentIds", array);
	}

	@POST
	@Path("/statistics")
	public Response getStatistics(
			@FormParam("server-key") String serverKey,
			@FormParam("statistics") String statisticsAsJSON) {
		
		Server server = this.service.getServer(serverKey);
		
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

				this.service.updateStatistics(serverKey, contextId, date, uncaughtExceptions, receivedMessages);
			}
			
			return ResponseUtils
					.ok();

		} catch (JSONException e) {
			return ResponseUtils.fail("Error parsing json");
		}
	}
	
	@POST
	@Path("/deployment/{deploymentId}/start")
	public Response startDeployment(@PathParam("deploymentId") long deploymentId, @FormParam("server-key") String serverKey) {
		String filename = this.deploymentService.startDeployment(deploymentId, serverKey);
		File fileToSend = new File(filename);
		return Response
				.ok(fileToSend, "application/zip")
				.build();
	}

	@POST
	@Path("/deployment/{deploymentId}/end-success")
	public Response endDeploymentSuccess(@PathParam("deploymentId") long deploymentId, @FormParam("server-key") String serverKey) {
		this.deploymentService.endDeploymentSuccessfully(deploymentId, serverKey);
		return Response
				.ok()
				.build();
	}

	@POST
	@Path("/deployment/{deploymentId}/end-failure")
	public Response endDeploymentErrors(@PathParam("deploymentId") long deploymentId, @FormParam("server-key") String serverKey) {
		this.deploymentService.endDeploymentWithErrors(deploymentId, serverKey);
		return Response
				.ok()
				.build();
	}
}
