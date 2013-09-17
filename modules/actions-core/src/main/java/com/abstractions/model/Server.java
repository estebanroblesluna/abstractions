package com.abstractions.model;

public class Server {

	private long id;

	private String name;
	private String ipDNS;
	private int port;

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
}
