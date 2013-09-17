package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.helper.Validate;

public class Deployment {

	long id;

	private User triggerBy;
	private ApplicationSnapshot snapshot;
	private List<Server> servers;
	private DeploymentState state;
	
	protected Deployment() {
		this.state = DeploymentState.PENDING;
	}

	public Deployment(ApplicationSnapshot snapshot, User user) {
		this();
		Validate.notNull(snapshot);
		Validate.notNull(user);
		
		this.snapshot = snapshot;
		this.triggerBy = user;
		this.servers = new ArrayList<Server>();
	}
	
	public void addServer(Server server) {
		Validate.notNull(server);

		this.servers.add(server);
	}

	public ApplicationSnapshot getSnapshot() {
		return this.snapshot;
	}

	public List<Server> getServers() {
		return Collections.unmodifiableList(this.servers);
	}

	public User getTriggerBy() {
		return triggerBy;
	}
	
	public long getId() {
		return this.id;
	}
	
	public String getServerList() {
		String list = "";
		for (Server server : this.getServers()) {
			if (!list.isEmpty()) {
				list = list + ", ";
			}
			list = list + server.getName();
		}
		return list;
	}

	public DeploymentState getState() {
		return state;
	}

	public void setState(DeploymentState state) {
		this.state = state;
	}
}
