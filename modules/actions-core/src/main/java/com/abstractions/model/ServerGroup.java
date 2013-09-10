package com.abstractions.model;

public class ServerGroup {

	long id;
	private String name;
	
	protected ServerGroup() { }

	public ServerGroup(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
