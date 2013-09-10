package com.abstractions.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;

public class User {

	long id;
	private String firstName;
	private String lastName;
	private List<Server> servers;
	
	public User() {
		this.servers = new ArrayList<Server>();
	}
	
	public void addServer(Server server) {
		Validate.notNull(server);
		
		this.servers.add(server);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
