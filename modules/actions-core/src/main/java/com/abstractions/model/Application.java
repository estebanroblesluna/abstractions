package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.helper.Validate;

public class Application {

	long id;
	private String name;
	private List<User>       builtByUsers;
	private List<Build>      builds;
	private List<Deployment> deploys;
	
	protected Application() { }
	
	public Application(String name) {
		this.name = name;
		this.builtByUsers = new ArrayList<User>();
		this.builds = new ArrayList<Build>();
		this.deploys = new ArrayList<Deployment>();
	}
	
	public void addUser(User user) {
		Validate.notNull(user);
		
		this.builtByUsers.add(user);
	}
	
	public void addBuild(Build build) {
		Validate.notNull(build);
		
		this.builds.add(build);
	}
	
	public void addDeployment(Deployment deployment) {
		Validate.notNull(deployment);
		
		this.deploys.add(deployment);
	}

	public List<User> getBuiltByUsers() {
		return Collections.unmodifiableList(builtByUsers);
	}

	public List<Build> getBuilds() {
		return Collections.unmodifiableList(builds);
	}

	public List<Deployment> getDeploys() {
		return Collections.unmodifiableList(deploys);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
