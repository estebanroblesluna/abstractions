package com.abstractions.model;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class LoggingInfoJSONMarshaller {

	public static LoggingInfo fromJSON(String json) throws JSONException {
		return fromJSON(new JSONObject(json));
	}
	
	public static LoggingInfo fromJSON(JSONObject root) throws JSONException {
		String applicationId = root.getString("applicationId");
		Date date = new Date(root.getLong("date"));
		LoggingInfo info = new LoggingInfo(applicationId, date);
		
		JSONObject logs = root.getJSONObject("logs");
		for (Iterator iterator = logs.keys(); iterator.hasNext();) {
			String elementId = (String) iterator.next();
			String log = logs.getString(elementId);
			info.addLog(elementId, log);
		}
		
		return info;
	}
	
	public static JSONObject toJSON(LoggingInfo info) throws JSONException {
		JSONObject applicationJSON = new JSONObject();
		
		if (info != null) {
			applicationJSON.put("applicationId", info.getApplicationId());
			applicationJSON.put("date", info.getDate().getTime());
			
			JSONObject logs = new JSONObject();
			for (Map.Entry<String, String> entry : info.getLogs().entrySet()) {
				logs.put(entry.getKey(), entry.getValue());
			}
	
			applicationJSON.put("logs", logs);
		}
		
		return applicationJSON;
	}
}
