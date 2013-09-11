package com.abstractions.model;

public class Server {

	long id;

	private String name;
	private ServerGroup group;
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

	public ServerGroup getGroup() {
		return group;
	}

	public void setGroup(ServerGroup group) {
		this.group = group;
	}

	public String getIpDNS() {
		return ipDNS;
	}

	public void setIpDNS(String ipDNS) {
		this.ipDNS = ipDNS;
	}
}
