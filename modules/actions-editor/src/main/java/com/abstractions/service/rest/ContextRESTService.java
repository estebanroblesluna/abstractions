package com.abstractions.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONObject;
import org.jsoup.nodes.Attribute;

import com.abstractions.service.DeploymentService;
import com.abstractions.service.core.ContextDefinition;
import com.abstractions.service.core.DevelopmentContextHolder;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;

@Path("/context")
public class ContextRESTService {

	private DevelopmentContextHolder holder;
	private NamesMapping mapping;
	private String serverId;
	private DeploymentService deploymentService;
	
	public ContextRESTService(DevelopmentContextHolder holder, NamesMapping mapping, String serverId, DeploymentService deploymentService) {
		this.holder = holder;
		this.mapping = mapping;
		this.serverId = serverId;
		this.deploymentService = deploymentService;
	}
	
	@POST
	@Path("/")
	public Response createContext() {
		ContextDefinition definition = new ContextDefinition(this.mapping);
		this.holder.put(definition);
		String contextId = definition.getId();
		
		return ResponseUtils.ok(
				new Attribute("id", contextId), 
				new Attribute("serverId", serverId));
	}
	
	@POST
	@Path("/{id}/sync")
	public Response sync(@PathParam("id") String contextId) {
		ContextDefinition context = this.holder.get(contextId);
		
		if (context == null)
		{
			return ResponseUtils.fail("Context not found");
		}
		
		try {
			context.sync();
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

}
