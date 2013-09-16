package com.abstractions.model;

public class Server {

	private long id;

	private String name;
	private String ipDNS;

	protected Server() { }
	
	public Server(String name, String ipDNS) {
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

}
