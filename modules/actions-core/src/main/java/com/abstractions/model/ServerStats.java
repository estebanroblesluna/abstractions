package com.abstractions.model;

import java.util.Date;
import java.util.Map;

public class ServerStats {

	long id;
	
	private Server server;
	private String contextId;
	private Date date;
	private long uncaughtExceptions;
	private Map<String, Long> receivedMessages;
	
	protected ServerStats() { }

	public ServerStats(Server server, String contextId, Date date, long uncaughtExceptions, Map<String, Long> receivedMessages) {
		this.server = server;
		this.contextId = contextId;
		this.date = date;
		this.uncaughtExceptions = uncaughtExceptions;
		this.receivedMessages = receivedMessages;
	}

	public String getContextId() {
		return contextId;
	}

	public Date getDate() {
		return date;
	}

	public long getUncaughtExceptions() {
		return uncaughtExceptions;
	}

	public Map<String, Long> getReceivedMessages() {
		return receivedMessages;
	}

	public Server getServer() {
		return server;
	}
}
