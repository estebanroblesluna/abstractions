package com.abstractions.model;

public class Flow implements Cloneable {

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
	
	@Override
	public Flow clone() throws CloneNotSupportedException {
		Flow flow = new Flow(name);
		flow.setJson(this.json);
		return flow;
	}
}
