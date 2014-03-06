package com.abstractions.service.rest;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.nodes.Attribute;

import com.abstractions.generalization.Abstracter;
import com.abstractions.generalization.MultipleEntryPointsException;
import com.abstractions.generalization.UnconnectedDefinitionsException;
import com.abstractions.meta.AbstractionDefinition;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.service.DeploymentService;
import com.abstractions.service.IconService;
import com.abstractions.service.core.LibraryService;
import com.abstractions.service.core.DevelopmentContextHolder;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.web.FlowController;

@Path("/context")
public class ContextRESTService {

	private DevelopmentContextHolder holder;
	private NamesMapping mapping;
	private String serverId;
	private DeploymentService deploymentService;
	private LibraryService libraryService;
        private IconService iconService;
	
	public ContextRESTService(
			DevelopmentContextHolder holder, 
			NamesMapping mapping, 
			String serverId, 
			DeploymentService deploymentService,
			LibraryService libraryService) {
		this.holder = holder;
		this.mapping = mapping;
		this.serverId = serverId;
		this.deploymentService = deploymentService;
		this.libraryService = libraryService;
	}
	
	@POST
	@Path("/")
	public Response createContext() {
		ApplicationDefinition appDefinition = new ApplicationDefinition("myApp");
		CompositeTemplate definition = appDefinition.createTemplate(this.mapping);
		
		this.holder.put(definition);
		String contextId = definition.getId();
		
		return ResponseUtils.ok(
				new Attribute("id", contextId), 
				new Attribute("serverId", serverId));
	}
	
	@POST
	@Path("/{id}/sync")
	public Response sync(@PathParam("id") String contextId) {
		CompositeTemplate context = this.holder.get(contextId);
		
		if (context == null)
		{
			return ResponseUtils.fail("Context not found");
		}
		
		try {
			context.sync(null, this.mapping);
			return ResponseUtils.ok();
		} catch (ServiceException e) {
			return ResponseUtils.fail("Error syncing context");
		}
	}
	
	@GET
	@Path("/{contextId}/profilingInfo/{deploymentId}")
	public Response profilingInfo(@PathParam("contextId") String contextId, @PathParam("deploymentId") long deploymentId) {
		JSONObject profilingInfo = this.deploymentService.getProfilingInfo(deploymentId, contextId);
		return ResponseUtils.ok("profilingInfo", profilingInfo);
	}
	
	@POST
	@Path("/{id}/abstract")
	public Response abstractAbstraction(
			@PathParam("id") String contextId,
			@FormParam("name") String name,
			@FormParam("displayName") String displayName,
			@FormParam("elementUrns") String elementUrnsAsString) {
		
		List<String> elementUrns = Arrays.asList(elementUrnsAsString.split(","));
		CompositeTemplate application = this.holder.get(contextId);
		
		Abstracter abstracter = new Abstracter();
		
		try {
			AbstractionDefinition abstraction = abstracter.abstractFrom(name, application, elementUrns);
			abstraction.setDisplayName(displayName);
			abstraction.setIcon(iconService.get(12)); //sets groovy icon
			this.libraryService.addTo(3, abstraction);
			this.mapping.addMapping(abstraction.getName(), abstraction);
			
			return ResponseUtils.ok("definition", FlowController.convertDefinition(abstraction));
		} catch (UnconnectedDefinitionsException e) {
			return ResponseUtils.fail("The subgraph is not connected!");
		} catch (MultipleEntryPointsException e) {
			return ResponseUtils.fail("The subgraph has multiple entry points! Please remove some and get 1 or less elements with incoming external connections");
 		} catch (JSONException e) {
			return ResponseUtils.fail("Error converting abstraction to JSON! Please refresh");
		}
		
	}
}
