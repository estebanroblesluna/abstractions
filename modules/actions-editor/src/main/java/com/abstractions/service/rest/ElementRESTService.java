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

import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.service.DeploymentService;
import com.abstractions.service.UserServiceImpl;
import com.abstractions.service.UserService;
import com.abstractions.service.core.DevelopmentContextHolder;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;


@Path("/element")
public class ElementRESTService {

	private static final Log log = LogFactory.getLog(ElementRESTService.class);
	
	private DevelopmentContextHolder holder;
	private NamesMapping mapping;
	private DeploymentService deploymentService;
  private UserService userService;
	
	public ElementRESTService(DevelopmentContextHolder holder, NamesMapping mapping, DeploymentService deploymentService, UserService userService) {
		this.holder = holder;
		this.mapping = mapping;
		this.deploymentService = deploymentService;
		this.userService = userService;
	}
	
	@POST
	@Path("/{applicationId}/{contextId}/{element}")
	public Response addElement(
          @PathParam("applicationId") String applicationId, 
	        @PathParam("contextId") String contextId, 
	        @PathParam("element") String elementName) {

	  ApplicationTemplate appTemplate = this.holder.getApplicationTemplate(this.userService.getCurrentUser(), applicationId);
		CompositeTemplate context = appTemplate.getFlow(contextId);
		
		ElementTemplate definition = new ElementTemplate(this.mapping.getDefinition(elementName));
		context.addDefinition(definition);
		
		return ResponseUtils.ok(new Attribute("id", definition.getId()));
	}
	
	@DELETE
	@Path("/{applicationId}/{contextId}/{elementId}")
	public Response deleteElement(
          @PathParam("applicationId") String applicationId, 
	        @PathParam("contextId") String contextId, 
	        @PathParam("elementId") String elementId) {
	  
    ApplicationTemplate appTemplate = this.holder.getApplicationTemplate(this.userService.getCurrentUser(), applicationId);
		CompositeTemplate context = appTemplate.getFlow(contextId);
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

	@POST
	@Path("/{contextId}/{elementId}/cache/computed/{deploymentId}")
	public Response addLazyComputedCache(
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String elementId, 
			@PathParam("deploymentId") long deploymentId,
			@FormParam("memcachedURL") String memcachedURL,
			@FormParam("ttl") String ttl,
			@FormParam("keyExpression") String keyExpression,
			@FormParam("cacheExpressions") String cacheExpressions)
	{
		this.deploymentService.addLazyComputedCache(
				deploymentId, 
				contextId, 
				elementId,
				memcachedURL,
				ttl,
				keyExpression,
				cacheExpressions);
		
		return ResponseUtils.ok();
	}
	
	@POST
	@Path("/{contextId}/{elementId}/cache/autorefreshable/{deploymentId}")
	public Response addLazyAutorefreshableCache(
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String elementId, 
			@PathParam("deploymentId") long deploymentId,
			@FormParam("memcachedURL") String memcachedURL,
			@FormParam("oldCacheEntryInMills") String oldCacheEntryInMills,
			@FormParam("keyExpression") String keyExpression,
			@FormParam("cacheExpressions") String cacheExpressions)
	{
		this.deploymentService.addLazyAutorefreshableCache(
				deploymentId, 
				contextId, 
				elementId,
				memcachedURL,
				oldCacheEntryInMills,
				keyExpression,
				cacheExpressions);
		
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
			@FormParam("afterExpression") String afterExpression,
			@FormParam("isBeforeConditional") boolean isBeforeConditional,
			@FormParam("isAfterConditional") boolean isAfterConditional,
			@FormParam("beforeConditionalExpressionValue") String beforeConditionalExpressionValue,
			@FormParam("afterConditionalExpressionValue") String afterConditionalExpressionValue
			)
	{
		this.deploymentService.addLogger(
				deploymentId, 
				contextId, 
				elementId, 
				beforeExpression, 
				afterExpression,
				isBeforeConditional,
				isAfterConditional,
				beforeConditionalExpressionValue,
				afterConditionalExpressionValue);
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
	@Path("/{applicationId}/{contextId}/{elementId}/{nextInChainId}/connection/{connectionType}")
	public Response addConnection(
	    @PathParam("applicationId") String applicationId, 
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String sourceId, 
			@PathParam("nextInChainId") String targetId,
			@PathParam("connectionType") String connectionType)
	{
    ApplicationTemplate appTemplate = this.holder.getApplicationTemplate(this.userService.getCurrentUser(), applicationId);
		CompositeTemplate context = appTemplate.getFlow(contextId);
		
		if (context == null) {
			return ResponseUtils.fail("Context not found");
		}
		
		ConnectionType type = ConnectionType.valueOf(connectionType);
		
		if (type == null) {
			return ResponseUtils.fail("Invalid type");
		}
		
		String connectionId = context.addConnection(sourceId, targetId, type).getId();
		return ResponseUtils.ok(new Attribute("id", connectionId));
	}
	
	@PUT
	@Path("/{applicationId}/{contextId}/{elementId}/breakpoint/{breakpoint}")
	public Response setBreakpoint(
      @PathParam("applicationId") String applicationId, 
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String elementId, 
			@PathParam("breakpoint") String breakpoint)
	{
    ApplicationTemplate appTemplate = this.holder.getApplicationTemplate(this.userService.getCurrentUser(), applicationId);
		CompositeTemplate context = appTemplate.getFlow(contextId);
		
		if (context == null) {
			return ResponseUtils.fail("Context not found");
		}

		ElementTemplate objectDefinition = context.getDefinition(elementId);

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
	@Path("/{applicationId}/{contextId}/{elementId}/property/{propertyName}")
	public Response setProperty(
	    @PathParam("applicationId") String applicationId, 
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String elementId, 
			@PathParam("propertyName") String propertyName, 
			@FormParam("propertyValue") String propertyValue)
	{
    ApplicationTemplate appTemplate = this.holder.getApplicationTemplate(this.userService.getCurrentUser(), applicationId);
		CompositeTemplate context = appTemplate.getFlow(contextId);
		ElementTemplate definition = context.getDefinition(elementId);
		definition.setProperty(propertyName, propertyValue);
		return ResponseUtils.ok();
	}
	
	@POST
	@Path("/{applicationId}/{contextId}/{elementId}/action/{actionName}")
	public Response performAction(
      @PathParam("applicationId") String applicationId, 
			@PathParam("contextId") String contextId, 
			@PathParam("elementId") String elementId, 
			@PathParam("actionName") String actionName, 
			@FormParam("arguments") String argumentsAsString)
	{
    ApplicationTemplate appTemplate = this.holder.getApplicationTemplate(this.userService.getCurrentUser(), applicationId);
		CompositeTemplate context = appTemplate.getFlow(contextId);
		ElementTemplate definition = context.getDefinition(elementId);
		String[] arguments = null;
		if (!StringUtils.isBlank(argumentsAsString)) {
			arguments = argumentsAsString.split(",");
		}
		try {
			Object result = definition.perform(actionName, arguments);
			String resultAsString = result == null 
					? "null"
					: result.toString();
			return ResponseUtils.ok(new Attribute("result", resultAsString));
		} catch (ServiceException e) {
			return ResponseUtils.fail("Error performing operation");
		}
	}
	
	@POST
	@Path("/{applicationId}/{contextId}/{elementId}/sync")
	public Response sync(
	        @PathParam("applicationId") String applicationId, 
	        @PathParam("contextId") String contextId, 
	        @PathParam("elementId") String elementId) {
    ApplicationTemplate appTemplate = this.holder.getApplicationTemplate(this.userService.getCurrentUser(), applicationId);
		CompositeTemplate context = appTemplate.getFlow(contextId);
		
		if (context == null) {
			return ResponseUtils.fail("Context not found");
		}
		
		ElementTemplate objectDefinition = context.getDefinition(elementId);

		if (objectDefinition == null) {
			return ResponseUtils.fail("Object not found");
		}

		if (context.getCompositeElement() == null) {
			try {
			  appTemplate.sync();
			} catch (ServiceException e) {
				return ResponseUtils.fail("Error syncing context");
			}
		} else {
			try {
				objectDefinition.initialize(context.getCompositeElement(), this.mapping, appTemplate);
			} catch (ServiceException e) {
				return ResponseUtils.fail("Error initializing object");
			}
		}
		
		return ResponseUtils.ok();
	}
}
