package com.abstractions.model;

import java.util.Date;

public class Server {

	private long id;

	private String name;
	private String ipDNS;
	private int port;
	private String key;
	private Date lastUpdate;
	private transient String amiScript;

	protected Server() {
		this.port = -1;
	}
	
	public Server(String name, String ipDNS) {
		this();
		this.name = name;
		this.ipDNS = ipDNS;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIpDNS() {
		return ipDNS;
	}

	public void setIpDNS(String ipDNS) {
		this.ipDNS = ipDNS;
	}

	public long getId() {
		return id;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getAmiScript() {
		return amiScript;
	}

	public void setAmiScript(String amiScript) {
		this.amiScript = amiScript;
	}
}
