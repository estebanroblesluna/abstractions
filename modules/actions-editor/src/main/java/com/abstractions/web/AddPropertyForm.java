package com.abstractions.web;

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
}
