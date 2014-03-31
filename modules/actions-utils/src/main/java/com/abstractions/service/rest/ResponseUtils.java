package com.abstractions.service.rest;

import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ResponseUtils {

	@SuppressWarnings("rawtypes")
	public static Response ok(Map.Entry... entries) {
		JSONObject json = new JSONObject();
		try {
			json.put("result", "success");
			for (int i = 0; i < entries.length; i++) {
				Map.Entry entry = entries[i];
				json.put(entry.getKey().toString(), entry.getValue().toString());
			}
			String output = json.toString();
			return Response
					.status(200)
					.entity(output)
					.type(MediaType.APPLICATION_JSON)
					.build();
		} catch (JSONException e) {
			return Response
					.status(500)
					.entity("Error creating response")
					.build();
		}
	}
	
	public static Response okJsons(Map.Entry<String, JSONObject>... entries) {
		JSONObject json = new JSONObject();
		try {
			json.put("result", "success");
			for (int i = 0; i < entries.length; i++) {
				Map.Entry<String, JSONObject> entry = entries[i];
				json.put(entry.getKey(), entry.getValue());
			}
			String output = json.toString();
			return Response
					.status(200)
					.entity(output)
					.type(MediaType.APPLICATION_JSON)
					.build();
		} catch (JSONException e) {
			return Response
					.status(500)
					.entity("Error creating response")
					.build();
		}
	}
	
	public static Response fail(String message) {
		return Response
				.status(500)
				.entity(message)
				.build();
	}

	public static Response ok(String key, JSONObject value) {
		JSONObject json = new JSONObject();
		try {
			json.put("result", "success");
			json.put(key, value);
			String output = json.toString();
			return Response
					.status(200)
					.entity(output)
					.type(MediaType.APPLICATION_JSON)
					.build();
		} catch (JSONException e) {
			return Response
					.status(500)
					.entity("Error creating response")
					.build();
		}
	}
	
	public static Response ok(String key, Object value) {
		JSONObject json = new JSONObject();
		try {
			json.put("result", "success");
			json.put(key, value);
			String output = json.toString();
			return Response
					.status(200)
					.entity(output)
					.type(MediaType.APPLICATION_JSON)
					.build();
		} catch (JSONException e) {
			return Response
					.status(500)
					.entity("Error creating response")
					.build();
		}
	}
}
