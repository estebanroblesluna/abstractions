package com.abstractions.model;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ProfilingInfoJSONMarshaller {

	public static ProfilingInfo fromJSON(String json) throws JSONException {
		return fromJSON(new JSONObject(json));
	}
	
	public static ProfilingInfo fromJSON(JSONObject root) throws JSONException {
		String applicationId = root.getString("applicationId");
		Date date = new Date(root.getLong("date"));
		ProfilingInfo info = new ProfilingInfo(applicationId, date);
		
		JSONObject averages = root.getJSONObject("averages");
		for (Iterator iterator = averages.keys(); iterator.hasNext();) {
			String elementId = (String) iterator.next();
			Double average = averages.getDouble(elementId);
			info.addAverage(elementId, average);
		}
		
		return info;
	}
	
	public static JSONObject toJSON(ProfilingInfo info) throws JSONException {
		JSONObject applicationJSON = new JSONObject();
		
		if (info != null) {
			applicationJSON.put("applicationId", info.getApplicationId());
			applicationJSON.put("date", info.getDate().getTime());
			
			JSONObject averages = new JSONObject();
			for (Map.Entry<String, Double> entry : info.getAverages().entrySet()) {
				averages.put(entry.getKey(), entry.getValue());
			}
	
			applicationJSON.put("averages", averages);
		}
		
		return applicationJSON;
	}
}
