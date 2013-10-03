package com.abstractions.service.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.nodes.Attribute;

import com.abstractions.service.DeploymentService;
import com.core.impl.ConnectionType;
import com.service.core.ContextDefinition;
import com.service.core.DevelopmentContextHolder;
import com.service.core.NamesMapping;
import com.service.core.ObjectDefinition;
import com.service.core.ServiceException;
import com.service.rest.ResponseUtils;

@Path("/element")
public class ElementRESTService {

	private static final Log log = LogFactory.getLog(ElementRESTService.class);
	
	private DevelopmentContextHolder holder;
	private NamesMapping mapping;
	private DeploymentService deploymentService;
	
	public ElementRESTService(DevelopmentContextHolder holder, NamesMapping mapping, DeploymentService deploymentService) {
		this.holder = holder;
		this.mapping = mapping;
		this.deploymentService = deploymentService;
	}
	
	@POST
	@Path("/{contextId}/{element}")
	public Response addElement(@PathParam("contextId") String contextId, @PathParam("element") String elementName)
	{
		ContextDefinition context = this.holder.get(contextId);
		
		ObjectDefinition definition = new ObjectDefinition(this.mapping.getDefinition(elementName));
		context.addDefinition(definition);
		
		return ResponseUtils.ok(new Attribute("id", definition.getId()));
	}
	
	@DELETE
	@Path("/{contextId}/{elementId}")
	public Response deleteElement(@PathParam("contextId") String contextId, @PathParam("elementId") String elementId)
	{
		ContextDefinition context = this.holder.get(contextId);
		List<String> ids = context.deleteDefinition(elementId);
		JSONObject deleted = new JSONObject();
		JSONArray array = new JSONArray();
		for (String id : ids) {
			array.put(id);
		}
		try {
			deleted.put("deleted", array);
		} catch (JSONException e) {
			log.warn("Error writing json", e);
		}
		return ResponseUtils.ok("deleted", deleted);
	}

	@PUT
	@Path("/{contextId}/{elementId}/cache/{deploymentId}")
	public Response addCache(@PathParam("contextId") String contextId, @PathParam("elementId") String elementId, @PathParam("deploymentId") long deploymentId)
	{
		this.deploymentService.addCache(deploymentId, contextId, elementId);
		return ResponseUtils.ok();
	}
	
	@PUT
	@Path("/{contextId}/{elementId}/profiler/{deploymentId}")
	public Response addProfiler(@PathParam("contextId") String contextId, @PathParam("elementId") String elementId, @PathParam("deploymentId") long deploymentId)
	{
		this.deploymentService.addProfiler(deploymentId, contextId, elementId);
		return ResponseUtils.ok();
	}

	@DELETE
	@Path("/{contextId}/{elementId}/profiler/{deploymentId}")
	public Response removeProfiler(@PathParam("contextId") String contextId, @PathParam("elementId") String elementId, @PathParam("deploymentId") long deploymentId)
	{
		this.deploymentService.removeProfiler(deploymentId, contextId, elementId);
		return ResponseUtils.ok();
	}

	@POST
	@Path("/{contextId}/{elementId}/logger/{deploymentId}")
	public Response addLogger(
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String elementId,
			@PathParam("deploymentId") long deploymentId,
			@FormParam("beforeExpression") String beforeExpression,
			@FormParam("afterExpression") String afterExpression)
	{
		this.deploymentService.addLogger(deploymentId, contextId, elementId, beforeExpression, afterExpression);
		return ResponseUtils.ok();
	}
	
	@DELETE
	@Path("/{contextId}/{elementId}/logger/{deploymentId}")
	public Response removeLogger(@PathParam("contextId") String contextId, @PathParam("elementId") String elementId, @PathParam("deploymentId") long deploymentId)
	{
		this.deploymentService.removeLogger(deploymentId, contextId, elementId);
		return ResponseUtils.ok();
	}
	
	@GET
	@Path("/{contextId}/{elementId}/logger/{deploymentId}")
	public Response getLogger(@PathParam("contextId") String contextId, @PathParam("elementId") String elementId, @PathParam("deploymentId") long deploymentId)
	{
		JSONObject result = this.deploymentService.getLogger(deploymentId, contextId, elementId);
		return ResponseUtils.ok("logger", result);
	}
	
	@POST
	@Path("/{contextId}/{elementId}/{nextInChainId}/connection/{connectionType}")
	public Response addConnection(
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String sourceId, 
			@PathParam("nextInChainId") String targetId,
			@PathParam("connectionType") String connectionType)
	{
		ContextDefinition context = this.holder.get(contextId);
		
		if (context == null) {
			return ResponseUtils.fail("Context not found");
		}
		
		ConnectionType type = ConnectionType.valueOf(connectionType);
		
		if (type == null) {
			return ResponseUtils.fail("Invalid type");
		}
		
		String connectionId = context.addConnection(sourceId, targetId, type);
		return ResponseUtils.ok(new Attribute("id", connectionId));
	}
	
	@PUT
	@Path("/{contextId}/{elementId}/breakpoint/{breakpoint}")
	public Response setBreakpoint(
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String elementId, 
			@PathParam("breakpoint") String breakpoint)
	{
		ContextDefinition context = this.holder.get(contextId);
		
		if (context == null) {
			return ResponseUtils.fail("Context not found");
		}

		ObjectDefinition objectDefinition = context.getDefinition(elementId);

		if (objectDefinition == null) {
			return ResponseUtils.fail("Object not found");
		}
		
		try {
			boolean hasBreakpoint = false;
			hasBreakpoint = Boolean.parseBoolean(breakpoint);
			objectDefinition.setHasBreakpoint(hasBreakpoint);
		} catch (Exception e) {
		}
		
		return ResponseUtils.ok();
	}
	
	@POST
	@Path("/{contextId}/{elementId}/property/{propertyName}")
	public Response setProperty(
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String elementId, 
			@PathParam("propertyName") String propertyName, 
			@FormParam("propertyValue") String propertyValue)
	{
		ContextDefinition context = this.holder.get(contextId);
		ObjectDefinition definition = context.getDefinition(elementId);
		definition.setProperty(propertyName, propertyValue);
		return ResponseUtils.ok();
	}
	
	@POST
	@Path("/{contextId}/{elementId}/action/{actionName}")
	public Response performAction(
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String elementId, 
			@PathParam("actionName") String actionName, 
			@FormParam("arguments") String argumentsAsString)
	{
		ContextDefinition context = this.holder.get(contextId);
		ObjectDefinition definition = context.getDefinition(elementId);
		String[] arguments = null;
		if (!StringUtils.isBlank(argumentsAsString)) {
			arguments = argumentsAsString.split(",");
		}
		try {
			definition.perform(actionName, arguments);
			return ResponseUtils.ok();
		} catch (ServiceException e) {
			return ResponseUtils.fail("Error performing operation");
		}
	}
	
	@POST
	@Path("/{contextId}/{elementId}/sync")
	public Response sync(@PathParam("contextId") String contextId, @PathParam("elementId") String elementId) {
		ContextDefinition context = this.holder.get(contextId);
		
		if (context == null) {
			return ResponseUtils.fail("Context not found");
		}
		
		ObjectDefinition objectDefinition = context.getDefinition(elementId);

		if (objectDefinition == null) {
			return ResponseUtils.fail("Object not found");
		}

		if (context.getContext() == null) {
			try {
				context.sync();
			} catch (ServiceException e) {
				return ResponseUtils.fail("Error syncing context");
			}
		} else {
			try {
				objectDefinition.initialize(context.getContext(), this.mapping);
			} catch (ServiceException e) {
				return ResponseUtils.fail("Error initializing object");
			}
		}
		
		return ResponseUtils.ok();
	}
}
