package com.abstractions.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Martin Aparicio Pons (martin.aparicio.pons@gmail.com)
 */
public class Connector implements Cloneable {

  private long id;
  private String name;
  private String type;
  private Map<String, String> configurations;

  protected Connector() {
  }

  public Connector(String name, String type, Map<String, String> configurations) {
    this.name = name;
    this.type = type;
    this.configurations = new HashMap<String, String>(configurations);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getId() {
    return id;
  }

  public Map<String, String> getConfigurations() {
    return configurations;
  }

  public void setConfigurations(Map<String, String> configurations) {
    this.configurations = configurations;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
  
  @Override
  public Connector clone() throws CloneNotSupportedException {
    Connector connector = new Connector(this.name, this.type, this.configurations);
    return connector;
  }
}
