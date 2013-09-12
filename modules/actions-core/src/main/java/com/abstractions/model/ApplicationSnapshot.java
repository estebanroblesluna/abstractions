package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.jsoup.helper.Validate;

public class ApplicationSnapshot {

	long id;
	
	private Date date;
	private List<Property> properties;

	public ApplicationSnapshot() {
		this.date = new Date();
		this.properties = new ArrayList<Property>();
	}

	public void addProperty(Property property) {
		Validate.notNull(property);

		this.properties.add(property);
	}

	public List<Property> getProperties() {
		return Collections.unmodifiableList(properties);
	}

	public Date getDate() {
		return date;
	}

	public long getId() {
		return id;
	}
	
	
}
