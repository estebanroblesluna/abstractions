package com.abstractions.server.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StatisticsInfo {

	private Map<String, Long> receivedMessages;
	private long uncaughtExceptions;
	private Date date;
	
	public StatisticsInfo(long uncaughtExceptions) {
		this.date = new Date();
		this.uncaughtExceptions = uncaughtExceptions;
		this.receivedMessages = new HashMap<String, Long>();
	}
	
	public void putReceivedMessages(String id, Long value) {
		this.receivedMessages.put(id, value);
	}

	public Map<String, Long> getReceivedMessages() {
		return receivedMessages;
	}

	public long getUncaughtExceptions() {
		return uncaughtExceptions;
	}

	public Date getDate() {
		return date;
	}
}
