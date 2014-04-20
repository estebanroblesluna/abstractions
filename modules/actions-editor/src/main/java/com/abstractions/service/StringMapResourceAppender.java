package com.abstractions.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.abstractions.model.Resource;

public class StringMapResourceAppender implements ResourceAppender {

	private Map<String, String> resources;
	
	public StringMapResourceAppender(Map<String, String> resources) {
		Validate.notNull(resources);
		
		this.resources = resources;
	}
	
	@Override
	public List<Resource> getResources() {
		List<Resource> resources = new ArrayList<Resource>();
		for (String path : this.resources.keySet()) {
			resources.add(new Resource(-1, path, this.resources.get(path).getBytes(), null));
		}
		return resources;
	}

}
