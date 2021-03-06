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
  private List<Connector> connectors;
  private Environment environment;

  protected ApplicationSnapshot() { }

  public ApplicationSnapshot(Application application) {
    this.date = new Date();
    this.flows = new ArrayList<Flow>();
    this.properties = new ArrayList<Property>();
    this.application = application;
    this.resources = new ArrayList<Resource>();
    this.connectors = new ArrayList<Connector>();
    this.setEnvironment(null);
  }

  public void addProperty(Property property) {
    Validate.notNull(property);

    this.properties.add(property);
  }

  public void addConnector(Connector connector) {
    Validate.notNull(connector);

    this.connectors.add(connector);
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

  public List<Resource> getResources() {
    return Collections.unmodifiableList(this.resources);
  }
  
  public List<Connector> getConnectors() {
    return Collections.unmodifiableList(this.connectors);
  }

  public void addResource(Resource resource) {
    this.resources.add(resource);
  }

  public Application getApplication() {
    return application;
  }

  /**
   * @return the environment
   */
  public Environment getEnvironment() {
    return environment;
  }

  /**
   * @param environment
   *          the environment to set
   */
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }

  public String getProperty(String propertyName) {
    for (Property property : this.properties) {
      if (property.getName().equals(propertyName))
        return property.getValue();
    }
    return null;
  }
}
