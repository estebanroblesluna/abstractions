package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jsoup.helper.Validate;

public class Build {

	long id;

	private Date date;
	private Application application;
	private ApplicationSnapshot snapshot;
	private List<Property> properties;
	
	protected Build() { }
	
	public Build(Application application, ApplicationSnapshot snapshot) {
		Validate.notNull(application);
		Validate.notNull(snapshot);
		
		this.date = new Date();
		this.application = application;
		this.snapshot = snapshot;
		this.properties = new ArrayList<Property>();
	}
	
	public void addProperty(Property property) {
		Validate.notNull(property);
		
		this.properties.add(property);
	}

	public Date getDate() {
		return date;
	}

	public Application getApplication() {
		return application;
	}

	public ApplicationSnapshot getSnapshot() {
		return snapshot;
	}

	public List<Property> getProperties() {
		return Collections.unmodifiableList(this.properties);
	}
}
