package com.abstractions.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProfilingInfo {

	long id;
	private Date date;
	private String serverId;
	private String applicationId;
	private Map<String, Double> averages;
	
	protected ProfilingInfo() {
		this.date = new Date();
		this.averages = new HashMap<String, Double>();
	}

	public ProfilingInfo(String applicationId) {
		this();
		this.applicationId = applicationId;
	}

	public ProfilingInfo(String applicationId, Date date) {
		this();
		this.applicationId = applicationId;
		this.date = date;
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

	public String getApplicationId() {
		return applicationId;
	}
	
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
}
