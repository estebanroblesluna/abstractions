package com.server.rest;

import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.nodes.Attribute;

import com.server.core.ActionsServer;
import com.server.core.ProfilingInfo;
import com.service.rest.ResponseUtils;

@Path("server")
public class ActionsServerRest {

	private static final Log log = LogFactory.getLog(ActionsServerRest.class);
	
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

	
	
	@Path("/{contextId}/profilingInfo")
	@GET
	public Response getProfilers(
			@PathParam("contextId") String contextId) {
		
		ProfilingInfo info = this.server.getProfilingInfo(contextId);
		
		JSONObject result = new JSONObject();
		try {
			result.put("date", info.getDate().getTime());
			JSONObject averages = new JSONObject();
			for (Map.Entry<String, Double> entry : info.getAverages().entrySet()) {
				averages.put(entry.getKey(), entry.getValue());
			}
			result.put("averages", averages);
		} catch (JSONException e) {
			log.warn("Error generating json", e);
		}
		
		return ResponseUtils.ok("profilingInfo", result);
	}
	
	@Path("/{contextId}/profile/{objectId}/")
	@PUT
	public Response addProfiler(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId) {
		
		this.server.addProfiler(contextId, objectId);
		return ResponseUtils.ok();
	}
	
	@Path("/{contextId}/profile/{objectId}/")
	@DELETE
	public Response removeProfiler(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId) {
		
		this.server.removeProfiler(contextId, objectId);
		return ResponseUtils.ok();
	}
}
