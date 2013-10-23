package com.abstractions.service.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class CompositeTemplateMarshaller {

	private NamesMapping mapping;
	
	public CompositeTemplateMarshaller(NamesMapping mapping) {
		this.mapping = mapping;
	}
	
	public String marshall(CompositeTemplate definition) throws MarshallingException {
		JSONObject root = new JSONObject();
		
		try {
			root.put("id", definition.getId());
			root.put("version", definition.getVersion());
			
			this.marshallDefinitions(definition, root);
		} catch (JSONException e) {
			throw new MarshallingException(e);
		}
		
		return root.toString();
	}

	private void marshallDefinitions(CompositeTemplate definition, JSONObject root) throws JSONException {
		JSONArray array = new JSONArray();

		List<ElementTemplate> postProcessableDefinitions = new ArrayList<ElementTemplate>();
		
		for (Entry<String, ElementTemplate> entry : definition.getDefinitions().entrySet())
		{
			if (this.isConnection(entry.getValue())) {
				postProcessableDefinitions.add(entry.getValue());
			} else {
				JSONObject jsonDefinition = this.marshall(entry.getValue());
				array.put(jsonDefinition);
			}
		}

		for (ElementTemplate postDefinition : postProcessableDefinitions)
		{
			JSONObject jsonDefinition = this.marshall(postDefinition);
			array.put(jsonDefinition);
		}

		root.put("definitions", array);
	}

	private boolean isConnection(ElementTemplate definition) {
		return definition.getElementName().endsWith("_CONNECTION");
	}

	private JSONObject marshall(ElementTemplate definition) throws JSONException {
		JSONObject root = new JSONObject();

		root.put("id", definition.getId());
		root.put("name", definition.getElementName());

		JSONObject properties = new JSONObject(definition.getProperties());
		
		properties.put("_breakpoint", definition.hasBreakpoint());
		root.put("properties", properties);
		
		
		return root;
	}
	
	public CompositeTemplate unmarshall(ApplicationDefinition application, String json) throws MarshallingException {
		try {
			JSONObject root = new JSONObject(json);
			
			CompositeTemplate definition = new CompositeTemplate(
					root.getString("id"), 
					application, 
					this.mapping);
			int version = root.getInt("version");
			definition.setVersion(version);
			
			this.parseDefinitions(root, definition);
			
			return definition;
		} catch (JSONException e) {
			throw new MarshallingException(e);
		}
	}

	private void parseDefinitions(JSONObject root, CompositeTemplate definition) throws JSONException {
		if (root.has("definitions")) {
			JSONArray definitions = root.getJSONArray("definitions");
			for (int i = 0; i < definitions.length(); i++) {
				JSONObject object = definitions.getJSONObject(i);
				ElementTemplate objectDefinition = this.parseDefinition(object);
				definition.addDefinition(objectDefinition);
			}
		}
	}

	private ElementTemplate parseDefinition(JSONObject object) throws JSONException {
		String id = object.getString("id");
		String name = object.getString("name");

		ElementDefinition elementDefinition = this.mapping.getDefinition(name);
		ElementTemplate definition = new ElementTemplate(id, elementDefinition);
		JSONObject propertiesAsJson = object.getJSONObject("properties");

		Map<String, String> properties = this.parsePropertiesMap(propertiesAsJson);
		definition.setProperties(properties);
		
		boolean hasBreakpoint = propertiesAsJson.has("_breakpoint") && propertiesAsJson.getBoolean("_breakpoint");
		definition.setHasBreakpoint(hasBreakpoint);
		
		return definition;
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String, String> parsePropertiesMap(JSONObject propertiesAsJson) throws JSONException {
		Map<String, String> properties = new HashMap<String, String>();
		
		Iterator i = propertiesAsJson.keys();
		while (i.hasNext())
		{
			String key = i.next().toString();
			String value = propertiesAsJson.get(key).toString();
			properties.put(key, value);
		}
		
		return properties;
	}
}
