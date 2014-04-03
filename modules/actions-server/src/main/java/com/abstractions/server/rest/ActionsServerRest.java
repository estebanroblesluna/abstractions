package com.abstractions.server.rest;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.abstractions.server.core.ActionsServer;
import com.abstractions.service.rest.ResponseUtils;

@Path("server")
public class ActionsServerRest {

	private static final Log log = LogFactory.getLog(ActionsServerRest.class);
	
	private ActionsServer server;
	
	public ActionsServerRest(ActionsServer server) {
		this.server = server;
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
}
