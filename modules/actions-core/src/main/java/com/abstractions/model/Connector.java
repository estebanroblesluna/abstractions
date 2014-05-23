package com.abstractions.model;

import java.util.Map;
import java.util.HashMap;

/**
 * 
 * @author Martin Aparicio Pons (martin.aparicio.pons@gmail.com)
 */
public class Connector {

  private long id;
  private String name;
  private String type;
  private Map<String, String> configurations;

  protected Connector() {
  }

  public Connector(String name, String type, Map<String, String> configurations) {
    this.name = name;
    this.type = type;
    this.configurations = configurations;
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
}
