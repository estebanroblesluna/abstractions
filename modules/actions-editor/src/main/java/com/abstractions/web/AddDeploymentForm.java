package com.abstractions.web;


public class AddDeploymentForm {

	private String[] servers;

	public AddDeploymentForm() {
		this.servers = new String[] {};
	}

	public String[] getServers() {
		return servers;
	}

	public void setServers(String[] servers) {
		this.servers = servers;
	}

}
