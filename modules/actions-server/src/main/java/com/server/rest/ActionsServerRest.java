package com.server.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jsoup.nodes.Attribute;

import com.server.core.ActionsServer;
import com.service.rest.ResponseUtils;

@Path("server")
public class ActionsServerRest {

	ActionsServer server;
	
	public ActionsServerRest(ActionsServer server) {
		this.server = server;
	}
	
	@Path("/start")
	@POST
	public Response start(@FormParam("contextDefinition") String contextDefinition) {
		this.server.start(contextDefinition);
		return ResponseUtils.ok();
	}
	
	@Path("/{contextId}/stop")
	@POST
	public Response stop(@PathParam("contextId") String contextId) {
		this.server.stop(contextId);
		return ResponseUtils.ok();
	}
	
	@Path("/{contextId}/running")
	@GET
	public Response isRunning(@PathParam("contextId") String contextId) {
		Boolean isRunning = this.server.isRunning(contextId);
		
		return ResponseUtils.ok(
				new Attribute("isRunning", isRunning.toString()));
	}
}
