package com.server.rest;

import javax.ws.rs.DELETE;
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
	
	@Path("/{contextId}/log")
	@GET
	public Response getLoggers(
			@PathParam("contextId") String contextId) {
		//TODO
		this.server.getLoggers(contextId);
		return ResponseUtils.ok();
	}
	
	@Path("/{contextId}/log/{objectId}/")
	@POST
	public Response addLogger(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId,
			@FormParam("logName") String logName,
			@FormParam("logExpression") String logExpression) {
		//TODO
		this.server.addLogger(contextId, objectId, logName, logExpression);
		return ResponseUtils.ok();
	}
	
	@Path("/{contextId}/log/{objectId}/{logName}")
	@DELETE
	public Response removeLogger(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId,
			@PathParam("logName") String logName) {
		//TODO
		this.server.removeLogger(contextId, objectId, logName);
		return ResponseUtils.ok();
	}
	
	@Path("/{contextId}/log/{objectId}/{logName}/{from}/{to}")
	@GET
	public Response getLogLines(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId,
			@PathParam("logName") String logName,
			@PathParam("from") Integer from,
			@PathParam("to") Integer to) {
		
		//TODO
		this.server.getLoggerLines(contextId, objectId, logName, from, to);
		return ResponseUtils.ok();
	}

	@Path("/{contextId}/profile")
	@GET
	public Response getProfilers(
			@PathParam("contextId") String contextId) {
		
		//TODO
		this.server.getProfilers(contextId);
		return ResponseUtils.ok();
	}
	
	@Path("/{contextId}/profile/{objectId}/")
	@POST
	public Response addProfiler(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId) {
		
		//TODO
		this.server.addProfiler(contextId, objectId);
		return ResponseUtils.ok();
	}
	
	@Path("/{contextId}/profile/{objectId}/")
	@DELETE
	public Response removeProfiler(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId) {
		
		//TODO
		this.server.removeProfiler(contextId, objectId);
		return ResponseUtils.ok();
	}
	
	@Path("/{contextId}/profile/{objectId}/")
	@GET
	public Response getProfilingInfo(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId) {
		
		//TODO
		this.server.getProfilingInfo(contextId, objectId);
		return ResponseUtils.ok();
	}
}
