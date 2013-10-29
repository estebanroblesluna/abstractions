package com.abstractions.service;

import java.io.InputStream;

import com.abstractions.service.core.ResourceService;

public class ResourceChange {

	private String path;
	private InputStream inputStream;
	private ResourceAction action;
	
	public ResourceChange(String path, InputStream content, ResourceAction action) {
		this.path = path;
		this.inputStream = content;
		this.action = action;
	}
	
	public void executeOn(ResourceService resourceService, long applicationId) {
		if (this.action.equals(ResourceAction.CREATE_OR_UPDATE)) {
			resourceService.storeResource(applicationId, this.path, this.inputStream);
		} else if (this.action.equals(ResourceAction.DELETE)) {
			resourceService.deleteResource(applicationId, this.path);
		}
	}

	public String getPath() {
		return path;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public ResourceAction getAction() {
		return action;
	}
	
}
