package com.abstractions.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

public class ServerCommand {

	long id;
	private DeploymentToServer deploymentToServer;
	private String name;
	private ServerCommandState state;
	private Map<String, String> arguments;
	
	protected ServerCommand() {
		this.state = ServerCommandState.PENDING;
		this.arguments = new HashMap<String, String>();
	}
	
	public ServerCommand(String name, DeploymentToServer deploymentToServer) {
		this();
		Validate.notNull(name);
		this.name = name;
		this.deploymentToServer = deploymentToServer;
	}

	public ServerCommandState getState() {
		return state;
	}

	public void setState(ServerCommandState state) {
		this.state = state;
	}

	public DeploymentToServer getDeploymentToServer() {
		return deploymentToServer;
	}

	public String getName() {
		return name;
	}

	public void addArgument(String name, String value) {
		Validate.notNull(name);
		this.arguments.put(name, value);
	}
	
	public Map<String, String> getArguments() {
		return Collections.unmodifiableMap(this.arguments);
	}

	public long getId() {
		return id;
	}
}
