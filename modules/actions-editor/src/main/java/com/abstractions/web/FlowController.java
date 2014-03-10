package com.abstractions.web;

import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abstractions.meta.AbstractionDefinition;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.ConnectionDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.meta.ElementDefinitionVisitor;
import com.abstractions.meta.MessageSourceDefinition;
import com.abstractions.meta.ProcessorDefinition;
import com.abstractions.meta.RouterDefinition;
import com.abstractions.model.Flow;
import com.abstractions.model.Library;
import com.abstractions.model.PropertyDefinition;
import com.abstractions.service.ApplicationService;
import com.abstractions.service.FlowService;
import com.abstractions.service.core.LibraryService;
import com.abstractions.service.TeamService;
import com.abstractions.service.core.DevelopmentContextHolder;
import com.abstractions.service.repository.MarshallingException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

@Controller
public class FlowController {

	@Autowired
	FlowService service;
	
	@Autowired
	DevelopmentContextHolder holder;
	
	@Autowired
	LibraryService libraryService;

        @Autowired
	ApplicationService applicationService;
        
        @Autowired
	TeamService teamService;
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/flows", method = RequestMethod.GET)
	public ModelAndView home(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("flows");
		String applicationName = this.applicationService.getApplication(applicationId).getName();
		List<Flow> flows = this.service.getFlows(teamId, applicationId);
                String teamName = this.teamService.getTeam(teamId).getName();
		mv.addObject("teamName", teamName);
		mv.addObject("flows", flows);
                mv.addObject("applicationName", applicationName);

		return mv;
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/flows/add", method = RequestMethod.GET)
	public ModelAndView add(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId) {
		ModelAndView mv = new ModelAndView("addFlow");
                String applicationName = this.applicationService.getApplication(applicationId).getName();
                String teamName = this.teamService.getTeam(teamId).getName();
                mv.addObject("applicationName", applicationName);
                mv.addObject("teamName", teamName);
		mv.addObject("teamId", teamId);
		mv.addObject("applicationId", applicationId);
		mv.addObject("libraries", getLibraries(libraryService));
		return mv;
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/flows/save/{flowId}", method = RequestMethod.POST, produces = { "application/json" })
	public String editFlowOnSave(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @PathVariable("flowId") long flowId, @ModelAttribute("form") AddFlowForm form) throws JSONException {
		CompositeTemplate context = this.holder.get(form.getName());
		
		JSONObject result = new JSONObject();
		
		if (context == null)
		{
			result.put("status", "failed");
			result.put("message", "Context not found");
			return result.toString();
		}
		
		try 
		{
			JSONObject dataRoot = new JSONObject(form.getJson());

			//process object definition position
			JSONArray positions = dataRoot.getJSONArray("positions");
			for (int i = 0; i < positions.length(); i++) {
				JSONObject position = positions.getJSONObject(i);
				
				String id = position.getString("id");
				String x = position.getString("x");
				String y = position.getString("y");
				
				ElementTemplate objectDefinition = context.getDefinition(id);
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
				
				ElementTemplate definition = context.getDefinition(id);
				definition.setProperty("points", builder.toString());
			}
		} 
		catch (JSONException e) 
		{
			result.put("status", "failed");
			result.put("message", "Failed parsing positions");
			return result.toString();
		}

		try {
			this.service.editFlow(teamId, applicationId, flowId, form.getName(), context);
		} catch (MarshallingException e) {
			result.put("status", "failed");
			result.put("message", "Error saving context to repository");
			return result.toString();
		}
		
		result.put("status", "success");
		return result.toString();
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/flows/save", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody String addFlow(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @ModelAttribute("form") AddFlowForm form) throws JSONException {
		CompositeTemplate context = this.holder.get(form.getName());
		
		JSONObject result = new JSONObject();
		
		if (context == null)
		{
			result.put("status", "failed");
			result.put("message", "Context not found");
			return result.toString();
		}
		
		try 
		{
			JSONObject dataRoot = new JSONObject(form.getJson());

			//process object definition position
			JSONArray positions = dataRoot.getJSONArray("positions");
			for (int i = 0; i < positions.length(); i++) {
				JSONObject position = positions.getJSONObject(i);
				
				String id = position.getString("id");
				String x = position.getString("x");
				String y = position.getString("y");
				
				ElementTemplate objectDefinition = context.getDefinition(id);
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
				
				ElementTemplate definition = context.getDefinition(id);
				definition.setProperty("points", builder.toString());
			}
		} 
		catch (JSONException e) 
		{
			result.put("status", "failed");
			result.put("message", "Failed parsing positions");
			return result.toString();
		}

		try {
			this.service.addFlow(applicationId, form.getName(), context);
		} catch (MarshallingException e) {
			result.put("status", "failed");
			result.put("message", "Error saving context to repository");
			return result.toString();
		}
		
		result.put("status", "success");
		return result.toString();
	}
	
	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/flows/edit/{flowId}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @PathVariable("flowId") long flowId) {
		ModelAndView mv = new ModelAndView("editFlow");
		
		Flow flow = this.service.loadFlow(teamId, applicationId, flowId);
                String applicationName = this.applicationService.getApplication(applicationId).getName();
                String teamName = this.teamService.getTeam(teamId).getName();
                mv.addObject("applicationName", applicationName);
                mv.addObject("teamName", teamName);
		mv.addObject("flow", flow);
		mv.addObject("libraries", getLibraries(libraryService));
		
		return mv;
	}

	@RequestMapping(value = "/teams/{teamId}/applications/{applicationId}/flows/remove", method = RequestMethod.POST)
	public String removeFlow(@PathVariable("teamId") long teamId, @PathVariable("applicationId") long applicationId, @ModelAttribute("form") RemoveForm form) {
		this.service.removeFlowsByIds(applicationId, form.getIdsToRemove());
		return "redirect:/teams/" + teamId + "/applications/" + applicationId + "/flows/";
	}
	
	public static String getLibraries(LibraryService libraryService) {
		JSONObject json = new JSONObject();
		JSONArray libraries = new JSONArray();

		try {
			for (Library library : libraryService.getCommonLibraries()) {
				libraries.put(asJSON(library));
			}
			json.put("libraries", libraries);
			
			return json.toString();
		} catch (JSONException e) {
			throw new IllegalArgumentException("Error writing libraries", e);
		}
	}
	
	private static JSONObject asJSON(Library library) throws JSONException {
		JSONObject libraryJSON = new JSONObject();

		libraryJSON.put("name", library.getName());
		libraryJSON.put("displayName", library.getDisplayName());
		
		JSONArray elementDefinitions = new JSONArray();
		
		for (ElementDefinition element : library.getDefinitions()) {
			final JSONObject elementDefinitionJSON = convertDefinition(element);
			
			elementDefinitions.put(elementDefinitionJSON);
		}
			
		libraryJSON.put("elements", elementDefinitions);
		
		return libraryJSON;
	}

	public static JSONObject convertDefinition(ElementDefinition element) throws JSONException {
		final JSONObject elementDefinitionJSON = new JSONObject();
		
		elementDefinitionJSON.put("name", element.getName());
		elementDefinitionJSON.put("displayName", element.getDisplayName());
		elementDefinitionJSON.put("type", element.getType());
		elementDefinitionJSON.put("icon", "/icon/" + element.getIcon().getId());

		//Add extra properties
		element.accept(new ElementDefinitionVisitor() {
			
			@Override
			public Object visitRouterDefinition(RouterDefinition routerDefinition) {
				return null;
			}
			
			@Override
			public Object visitProcessorDefinition(ProcessorDefinition processorDefinition) {
				return null;
			}
			
			@Override
			public Object visitMessageSourceDefinition(MessageSourceDefinition messageSourceDefinition) {
				return null;
			}
			
			@Override
			public Object visitConnectionDefinition(ConnectionDefinition connection) {
				try {
					elementDefinitionJSON.put("color", connection.getColor());
					elementDefinitionJSON.put("acceptedSourceTypes", connection.getAcceptedSourceTypes());
					elementDefinitionJSON.put("acceptedSourceMax", connection.getAcceptedSourceMax());
					elementDefinitionJSON.put("acceptedTargetTypes", connection.getAcceptedTargetTypes());
					elementDefinitionJSON.put("acceptedTargetMax", connection.getAcceptedTargetMax());
				} catch (JSONException e) {
				}
				return null;
			}
			
			@Override
			public Object visitApplicationDefinition(ApplicationDefinition applicationDefinition) {
				return null;
			}
			
			@Override
			public Object visitAbstractionDefinition(AbstractionDefinition abstractionDefinition) {
				try {
					elementDefinitionJSON.put("startingDefinitionId", abstractionDefinition.getStartingDefinition().getId());
				} catch (JSONException e) {
				}
				return null;
			}
		});
		
		JSONArray propertyDefinitions = new JSONArray();

		for (PropertyDefinition property : element.getProperties()) {
			JSONObject propertyDefinitionJSON = new JSONObject();
			
			propertyDefinitionJSON.put("name", property.getName());
			propertyDefinitionJSON.put("displayName", property.getDisplayName());
			propertyDefinitionJSON.put("type", property.getType());
			propertyDefinitionJSON.put("defaultValue", property.getDefaultValue());
			
			propertyDefinitions.put(propertyDefinitionJSON);
		}

		elementDefinitionJSON.put("properties", propertyDefinitions);
		return elementDefinitionJSON;
	}
}
