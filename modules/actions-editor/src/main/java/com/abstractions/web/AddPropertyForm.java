package com.abstractions.web;

import com.abstractions.model.Environment;

public class AddPropertyForm {

	private String name;
	private String value;
    private Environment environment;
        
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
        
        public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
}
