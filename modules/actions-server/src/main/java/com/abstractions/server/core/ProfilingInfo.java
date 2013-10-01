package com.abstractions.server.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfilingInfo {

	private Date date;
	private Map<String, Double> averages;
	
	public ProfilingInfo() {
		this.date = new Date();
		this.averages = new HashMap<String, Double>();
	}
	
	public void addAverage(String objectId, Double average) {
		this.averages.put(objectId, average);
	}

	public Date getDate() {
		return date;
	}

	public Map<String, Double> getAverages() {
		return averages;
	}
}
