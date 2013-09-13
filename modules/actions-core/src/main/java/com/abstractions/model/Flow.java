package com.abstractions.model;

public class Flow {

	long id;
	String name;
	String json;
	
	protected Flow() {}
	
	public Flow(String name) {
		this.name = name;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
