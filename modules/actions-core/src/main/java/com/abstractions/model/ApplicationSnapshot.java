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

	public ApplicationSnapshot() {
		this.date = new Date();
		this.flows = new ArrayList<Flow>();
		this.properties = new ArrayList<Property>();
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

	public Application getApplication() {
		return application;
	}
}
