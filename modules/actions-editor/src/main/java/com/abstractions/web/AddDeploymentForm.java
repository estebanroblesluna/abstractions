package com.abstractions.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class AddDeploymentForm {

	private String servers;

	public AddDeploymentForm() {
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}
	
	public Collection<Long> getServerIds() {
		List<Long> ids = new ArrayList<Long>();
		for (String id : this.servers.split(",")) {
			ids.add(Long.parseLong(id));
		}
		return ids;
	}

}
