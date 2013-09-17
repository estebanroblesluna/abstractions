package com.abstractions.service.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.jsoup.nodes.Attribute;

import com.service.core.ContextDefinition;
import com.service.core.DevelopmentContextHolder;
import com.service.core.NamesMapping;
import com.service.core.ServiceException;
import com.service.repository.ContextRepository;
import com.service.rest.ResponseUtils;

@Path("/context")
public class ContextRESTService {

	private DevelopmentContextHolder holder;
	private NamesMapping mapping;
	private ContextRepository repository;
	private String serverId;
	
	public ContextRESTService(DevelopmentContextHolder holder, NamesMapping mapping, String serverId, ContextRepository repository) {
		this.holder = holder;
		this.mapping = mapping;
		this.serverId = serverId;
		this.repository = repository;
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
}
