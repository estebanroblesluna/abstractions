package com.abstractions.model;

public class Environment {

	long id;

	private String name;
	
	protected Environment() { }

	public Environment(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
