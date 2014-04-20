package com.abstractions.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class LoggingInfo {

	long id;
	private Date date;
	private String serverId;
	private String applicationId;
	private Map<String, String> logs;
	
	protected LoggingInfo() {
		this.date = new Date();
		this.logs = new HashMap<String, String>();
	}

	public LoggingInfo(String applicationId) {
		this();
		this.applicationId = applicationId;
	}

	public LoggingInfo(String applicationId, Date date) {
		this();
		this.applicationId = applicationId;
		this.date = date;
	}

	public void addLog(String objectId, String log) {
		if (!StringUtils.isEmpty(log)) {
			this.logs.put(objectId, log);
		}
	}

	public Date getDate() {
		return date;
	}

	public Map<String, String> getLogs() {
		return logs;
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
	
	public boolean isEmpty() {
		return this.logs.isEmpty();
	}
}
