package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.helper.Validate;

public class ServerGroup {

	long id;
	private String name;
	private Environment environment;
	private List<Server> servers;
	private Team team;
	
	protected ServerGroup() { }

	public ServerGroup(String name, Team team) {
		this.name = name;
		this.environment = Environment.DEV;
		this.servers = new ArrayList<Server>();
		this.team = team;
	}

	public String getName() {
		return name;
	}
	
	public void addServer(Server server) {
		Validate.notNull(server);
		
		this.servers.add(server);
	}

	public List<Server> getServers() {
		return Collections.unmodifiableList(this.servers);
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public Team getTeam() {
		return this.team;
	}
	
	public long getId() {
		return this.id;
	}
	
}
