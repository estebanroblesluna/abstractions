package com.abstractions.web;

import com.abstractions.model.Environment;

public class AddServerGroupForm {

	private String name;
        private Environment environment;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
        
        public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
}
