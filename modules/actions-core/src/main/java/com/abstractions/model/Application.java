package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.helper.Validate;

public class Application {

	private long id;
	private String name;
	private List<Property> properties;
	private List<ApplicationSnapshot> snapshots;
	private List<Deployment> deploys;

	protected Application() {
	}

	public Application(String name) {
		this.name = name;
		this.snapshots = new ArrayList<ApplicationSnapshot>();
		this.deploys = new ArrayList<Deployment>();
		this.properties = new ArrayList<Property>();
	}
	
	public void addBuild(ApplicationSnapshot snapshot) {
		Validate.notNull(snapshot);

		this.snapshots.add(snapshot);
	}

	public void addDeployment(Deployment deployment) {
		Validate.notNull(deployment);

		this.deploys.add(deployment);
	}
	
	public void addProperty(Property property) {
		Validate.notNull(property);

		this.properties.add(property);
	}

	public List<ApplicationSnapshot> getSnapshots() {
		return Collections.unmodifiableList(this.snapshots);
	}

	public List<Deployment> getDeploys() {
		return Collections.unmodifiableList(deploys);
	}
	
	public List<Property> getProperties() {
		return Collections.unmodifiableList(properties);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public long getId() {
		return this.id;
	}
	
}
