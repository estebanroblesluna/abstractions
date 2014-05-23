package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsoup.helper.Validate;

public class Team {

  long id;

  private String name;
  private UserImpl owner;
  private List<ServerGroup> serverGroups;
  private List<Connector> connectors;
  private List<Application> applications;
  private List<UserImpl> users;

  protected Team() {
  }

  public Team(String name, UserImpl owner) {
    Validate.notNull(owner);
    this.name = name;
    this.serverGroups = new ArrayList<ServerGroup>();
    this.applications = new ArrayList<Application>();
    this.connectors = new ArrayList<Connector>();
    this.users = new ArrayList<UserImpl>();
    this.owner = owner;
    this.addUser(owner);
  }

  public String getName() {
    return name;
  }

  public void addServerGroup(ServerGroup group) {
    Validate.notNull(group);

    this.serverGroups.add(group);
  }

  public void addConnector(Connector connector) {
    Validate.notNull(connector);

    this.connectors.add(connector);
  }

  public void addApplication(Application application) {
    Validate.notNull(application);

    this.applications.add(application);
  }

  public void addUser(UserImpl user) {
    Validate.notNull(user);

    this.users.add(user);
    user.addTeam(this);
  }

  public List<ServerGroup> getServerGroups() {
    return Collections.unmodifiableList(serverGroups);
  }

  public List<Application> getApplications() {
    return Collections.unmodifiableList(applications);
  }

  public List<UserImpl> getUsers() {
    return Collections.unmodifiableList(users);
  }

  public List<Connector> getConnectors() {
    return Collections.unmodifiableList(connectors);
  }

  public long getId() {
    return id;
  }

  public UserImpl getOwner() {
    return owner;
  }
}
