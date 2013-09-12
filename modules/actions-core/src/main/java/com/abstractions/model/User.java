package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;

public class User {

	long id;
	private String firstName;
	private String lastName;
	private List<Team> teams;
	
	public User() {
		this.teams = new ArrayList<Team>();
	}
	
	public void addTeam(Team team) {
		Validate.notNull(team);
		
		this.teams.add(team);
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

	public List<Team> getTeams() {
		return Collections.unmodifiableList(this.teams);
	}
}
