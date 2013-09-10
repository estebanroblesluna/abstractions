package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.helper.Validate;

public class Deployment {

	long id;

	private User triggerBy;
	private Build build;
	private List<Server> servers;
	
	protected Deployment() { }

	public Deployment(Build build, User user) {
		Validate.notNull(build);
		Validate.notNull(user);
		
		this.build = build;
		this.triggerBy = user;
		this.servers = new ArrayList<Server>();
	}
	
	public void addServer(Server server) {
		Validate.notNull(server);

		this.servers.add(server);
	}

	public Build getBuild() {
		return build;
	}

	public List<Server> getServers() {
		return Collections.unmodifiableList(this.servers);
	}

	public User getTriggerBy() {
		return triggerBy;
	}
}
