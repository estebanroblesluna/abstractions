package com.abstractions.server.rest;

import java.util.List;
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

import com.abstractions.server.core.ActionsServer;
import com.abstractions.server.core.ProfilingInfo;
import com.abstractions.service.rest.ResponseUtils;

@Path("server")
public class ActionsServerRest {

	private static final Log log = LogFactory.getLog(ActionsServerRest.class);
	
	private ActionsServer server;
	
	public ActionsServerRest(ActionsServer server) {
		this.server = server;
	}
	
	@Path("/{contextId}/running")
	@GET
	public Response isRunning(@PathParam("contextId") String contextId) {
		Boolean isRunning = this.server.isRunning(contextId);
		
		return ResponseUtils.ok(
				new Attribute("isRunning", isRunning.toString()));
	}
	
	@Path("/{contextId}/log/{objectId}/")
	@GET
	public Response getLoggers(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId) {

		List<String> lines = this.server.getLogLines(contextId, objectId);
		
		JSONObject result = new JSONObject();
		try {
			result.put(objectId, lines);
		} catch (JSONException e) {
			log.warn("Error generating json", e);
		}
		
		return ResponseUtils.ok("logger", result);
	}
	
	@Path("/{contextId}/log/{objectId}/")
	@POST
	public Response addLogger(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId,
			@FormParam("beforeExpression") String beforeExpression,
			@FormParam("afterExpression") String afterExpression,
			@FormParam("isBeforeConditional") boolean isBeforeConditional,
			@FormParam("isAfterConditional") boolean isAfterConditional,
			@FormParam("beforeConditionalExpressionValue") String beforeConditionalExpressionValue,
			@FormParam("afterConditionalExpressionValue") String afterConditionalExpressionValue
			) {

		this.server.addLogger(
				contextId, 
				objectId, 
				beforeExpression, 
				afterExpression,
				isBeforeConditional,
				isAfterConditional,
				beforeConditionalExpressionValue,
				afterConditionalExpressionValue);
		return ResponseUtils.ok();
	}
	
	@Path("/{contextId}/log/{objectId}")
	@DELETE
	public Response removeLogger(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId) {
		
		this.server.removeLogger(contextId, objectId);
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
	
	@Path("/{contextId}/cache/{objectId}/")
	@POST
	public Response addCache(
			@PathParam("contextId") String contextId,
			@PathParam("objectId") String objectId,
			@FormParam("memcachedURL") String memcachedURL,
			@FormParam("keyExpression") String keyExpression,
			@FormParam("cacheExpressions") String cacheExpressions,
			@FormParam("ttl") int ttl) {
		
		this.server.addLazyAutorefreshableCache(contextId, objectId, memcachedURL, keyExpression, cacheExpressions, ttl);
		return ResponseUtils.ok();
	}
}
