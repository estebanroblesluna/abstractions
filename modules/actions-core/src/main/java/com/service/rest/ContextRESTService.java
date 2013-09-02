package com.service.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.nodes.Attribute;

import com.service.core.ContextDefinition;
import com.service.core.ContextHolder;
import com.service.core.DeploymentService;
import com.service.core.NamesMapping;
import com.service.core.ObjectDefinition;
import com.service.core.ServiceException;
import com.service.repository.ContextRepository;
import com.service.repository.MarshallingException;

@Path("/context")
public class ContextRESTService {

	private ContextHolder holder;
	private NamesMapping mapping;
	private ContextRepository repository;
	private String serverId;
	private DeploymentService deploymentService;
	
	public ContextRESTService(ContextHolder holder, NamesMapping mapping, String serverId, ContextRepository repository, DeploymentService deploymentService) {
		this.holder = holder;
		this.mapping = mapping;
		this.serverId = serverId;
		this.repository = repository;
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

	@POST
	@Path("/{id}/save")
	public Response save(
			@PathParam("id") String contextId, 
			@FormParam("data") String json) {
		ContextDefinition context = this.holder.get(contextId);
		
		if (context == null)
		{
			return ResponseUtils.fail("Context not found");
		}
		
		try 
		{
			JSONObject dataRoot = new JSONObject(json);

			//process object definition position
			JSONArray positions = dataRoot.getJSONArray("positions");
			for (int i = 0; i < positions.length(); i++) {
				JSONObject position = positions.getJSONObject(i);
				
				String id = position.getString("id");
				String x = position.getString("x");
				String y = position.getString("y");
				
				ObjectDefinition objectDefinition = context.getDefinition(id);
				if (objectDefinition != null) {
					objectDefinition.setProperty("x", x);
					objectDefinition.setProperty("y", y);
				}
			}

			//process connections
			JSONArray connections = dataRoot.getJSONArray("connections");
			for (int i = 0; i < connections.length(); i++) {
				JSONObject connection = connections.getJSONObject(i);
				
				String id = connection.getString("id");
				JSONArray points = connection.getJSONArray("points");

				StringBuilder builder = new StringBuilder();
				for (int j = 0; j < points.length(); j++) {
					JSONObject point = points.getJSONObject(j);
					String x = point.getString("x");
					String y = point.getString("y");

					builder.append(x);
					builder.append(",");
					builder.append(y);
					builder.append(";");
				}
				
				ObjectDefinition definition = context.getDefinition(id);
				definition.setProperty("points", builder.toString());
			}
		} 
		catch (JSONException e) 
		{
			return ResponseUtils.fail("Failed parsing positions");
		}

		try {
			this.repository.save(context);
		} catch (MarshallingException e) {
			return ResponseUtils.fail("Error saving context to repository");
		}
		
		return ResponseUtils.ok();
	}
	
	@POST
	@Path("/{id}/load")
	public Response load(@PathParam("id") String contextId) {
		ContextDefinition context;
		
		try 
		{
			context = this.repository.load(contextId);
			context.instantiate();
		} 
		catch (MarshallingException e) 
		{
			return ResponseUtils.fail("Error loading context from repository");
		} 
		catch (ServiceException e) 
		{
			return ResponseUtils.fail("Error initializing context");
		}
		
		context.initialize();
		this.holder.put(context);
		
		String json = this.repository.getJsonDefinition(contextId);
		return ResponseUtils.ok(
				new Attribute("serverId", serverId), 
				new Attribute("contextDefinition", json));
	}
	
	@POST
	@Path("/{id}/deploy/{serverHost}/{serverPort}")
	public Response deploy(
			@PathParam("id") String contextId, 
			@PathParam("serverHost") String serverHost,
			@PathParam("serverPort") String serverPort
			) {
		
		this.deploymentService.deploy(contextId, serverHost, serverPort);
		return ResponseUtils.ok();
	}

}
