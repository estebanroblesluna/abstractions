package com.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.core.meta.ConnectionDefinition;
import com.core.meta.ElementDefinition;
import com.core.meta.ElementDefinitionType;
import com.core.meta.Library;
import com.core.meta.Meta;
import com.core.meta.PropertyDefinition;

@Path("/library")
public class LibraryRESTService {

	private static Log log = LogFactory.getLog(LibraryRESTService.class);
	
	@GET
	@Path("/")
	public Response getLibraries() {
		JSONObject json = new JSONObject();
		JSONArray libraries = new JSONArray();

		try {
			libraries.put(this.asJSON(Meta.getCommonLibrary()));
			libraries.put(this.asJSON(Meta.getModulesLibrary()));
			json.put("libraries", libraries);
		} catch (JSONException e) {
			log.error("Error writing json", e);
		}
		
		return ResponseUtils.ok("libraries", json);
	}
	
	private JSONObject asJSON(Library library) throws JSONException {
		JSONObject libraryJSON = new JSONObject();

		libraryJSON.put("name", library.getName());
		libraryJSON.put("displayName", library.getDisplayName());
		
		JSONArray elementDefinitions = new JSONArray();
		
		for (ElementDefinition element : library.getDefinitions()) {
			JSONObject elementDefinitionJSON = new JSONObject();
			
			elementDefinitionJSON.put("name", element.getName());
			elementDefinitionJSON.put("displayName", element.getDisplayName());
			elementDefinitionJSON.put("type", element.getType());
			elementDefinitionJSON.put("icon", element.getIcon());

			if (ElementDefinitionType.CONNECTION.equals(element.getType())) {
				ConnectionDefinition connection = ((ConnectionDefinition) element);
				elementDefinitionJSON.put("color", connection.getColor());
				elementDefinitionJSON.put("acceptedSourceTypes", connection.getAcceptedSourceTypes());
				elementDefinitionJSON.put("acceptedSourceMax", connection.getAcceptedSourceMax());
				elementDefinitionJSON.put("acceptedTargetTypes", connection.getAcceptedTargetTypes());
				elementDefinitionJSON.put("acceptedTargetMax", connection.getAcceptedTargetMax());
			}
			
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
			
			elementDefinitions.put(elementDefinitionJSON);
		}
			
		libraryJSON.put("elements", elementDefinitions);
		
		return libraryJSON;
	}
}
