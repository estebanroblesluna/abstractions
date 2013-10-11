package com.abstractions.model;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.helper.Validate;

public class Deployment {

	long id;

	private User triggerBy;
	private ApplicationSnapshot snapshot;
	private List<DeploymentToServer> toServers;
	
	public Deployment() { }

	public Deployment(ApplicationSnapshot snapshot, User user) {
		Validate.notNull(snapshot);
		Validate.notNull(user);
		
		this.snapshot = snapshot;
		this.triggerBy = user;
		this.toServers = new ArrayList<DeploymentToServer>();
	}
	
	public void addServer(Server server) {
		Validate.notNull(server);

		DeploymentToServer toServer = new DeploymentToServer(this, server);
		this.toServers.add(toServer);
	}

	public ApplicationSnapshot getSnapshot() {
		return this.snapshot;
	}

	public List<Server> getServers() {
		List<Server> servers = new ArrayList<Server>();
		for (DeploymentToServer toServer : this.toServers) {
			servers.add(toServer.getServer());
		}
		return servers;
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

	public DeploymentToServer getToServer(long id) {
		for (DeploymentToServer toServer : this.toServers) {
			if (toServer.getServer().getId() == id) {
				return toServer;
			}
		}
		return null;
	}
}
