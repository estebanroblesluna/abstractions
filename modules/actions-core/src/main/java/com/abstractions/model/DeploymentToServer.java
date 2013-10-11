package com.abstractions.model;

import java.util.Date;

import org.jsoup.helper.Validate;

public class DeploymentToServer {

	long id;
	
	private Date lastUpdate;
	private Deployment deployment;
	private DeploymentState state;
	private Server server;
	
	protected DeploymentToServer() { }
	
	public DeploymentToServer(Deployment deployment, Server server) {
		Validate.notNull(deployment);
		Validate.notNull(server);
		
		this.lastUpdate = new Date();
		this.deployment = deployment;
		this.server = server;
		this.state = DeploymentState.PENDING;
	}

	public DeploymentState getState() {
		return state;
	}

	public void setState(DeploymentState state) {
		this.state = state;
		this.lastUpdate = new Date();
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public Deployment getDeployment() {
		return deployment;
	}

	public Server getServer() {
		return server;
	}
}
