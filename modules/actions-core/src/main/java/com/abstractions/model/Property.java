package com.abstractions.model;

public class Property {

	long id;

	private String name;
	private String value;
	private Environment environment;

	protected Property() { }

	public Property(String name, String value, Environment environment) {
		this.name = name;
		this.value = value;
		this.environment = environment;
	}
	
	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
