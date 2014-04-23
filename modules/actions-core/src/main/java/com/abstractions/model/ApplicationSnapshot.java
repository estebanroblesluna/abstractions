package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jsoup.helper.Validate;

public class ApplicationSnapshot {

	long id;
	
	private Date date;
	private List<Flow> flows;
	private List<Property> properties;
	private Application application;
	private List<Resource> resources;

	public ApplicationSnapshot(){
		
	}
	
	public ApplicationSnapshot(Application application) {
		this.date = new Date();
		this.flows = new ArrayList<Flow>();
		this.properties = new ArrayList<Property>();
		this.application = application;
		this.resources = new ArrayList<Resource>();
	}

	public void addProperty(Property property) {
		Validate.notNull(property);

		this.properties.add(property);
	}

	public List<Property> getProperties() {
		return Collections.unmodifiableList(this.properties);
	}
	
	public void addFlow(Flow flow) {
		Validate.notNull(flow);

		this.flows.add(flow);
	}
	
	public List<Flow> getFlows() {
		return Collections.unmodifiableList(this.flows);
	}

	public Date getDate() {
		return date;
	}

	public long getId() {
		return id;
	}
	
	public List<Resource> getResources(){
		return Collections.unmodifiableList(this.resources);
	}
	
	public void addResource(Resource resource){
		this.resources.add(resource);
	}

	public Application getApplication() {
		return application;
	}
}
