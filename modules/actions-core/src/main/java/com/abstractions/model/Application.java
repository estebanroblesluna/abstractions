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
	private List<Flow> flows;
	private Team team;

	protected Application() {
		this.snapshots = new ArrayList<ApplicationSnapshot>();
		this.deploys = new ArrayList<Deployment>();
		this.properties = new ArrayList<Property>();
		this.flows = new ArrayList<Flow>();
	}

	public Application(String name, Team team) {
		Validate.notNull(team);

		this.name = name;
		this.team = team;
		this.snapshots = new ArrayList<ApplicationSnapshot>();
		this.deploys = new ArrayList<Deployment>();
		this.properties = new ArrayList<Property>();
		this.flows = new ArrayList<Flow>();
	}
	
	public void addSnapshot(ApplicationSnapshot snapshot) {
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
	
	public void addFlow(Flow flow) {
		Validate.notNull(flow);

		this.flows.add(flow);
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
	
	public List<Property> getProperties(Environment env) {
		List<Property> ret = new ArrayList<Property>();
		for(Property p : this.properties ){
			if(p.getEnvironment().equals(env))
				ret.add(p);
		}
		return Collections.unmodifiableList(ret);
	}
	
	public List<Flow> getFlows() {
		return Collections.unmodifiableList(flows);
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

	public Team getTeam() {
		return team;
	}
	
	public String getProperty(String propertyName, Environment env){
		for(Property property : this.getProperties(env)){
			if(property.getName().equals(propertyName))
				return property.getValue();
		}
		return null;
	}
}
