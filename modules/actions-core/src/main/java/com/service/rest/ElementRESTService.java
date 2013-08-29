package com.service.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Attribute;

import com.core.impl.ConnectionType;
import com.service.core.ContextDefinition;
import com.service.core.ContextHolder;
import com.service.core.NamesMapping;
import com.service.core.ObjectDefinition;
import com.service.core.ServiceException;

@Path("/element")
public class ElementRESTService {

	private ContextHolder holder;
	private NamesMapping mapping;
	
	public ElementRESTService(ContextHolder holder, NamesMapping mapping) {
		this.holder = holder;
		this.mapping = mapping;
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
		context.deleteDefinition(elementId);
		return ResponseUtils.ok();
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
