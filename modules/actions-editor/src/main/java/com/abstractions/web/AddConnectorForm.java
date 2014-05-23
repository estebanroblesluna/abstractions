package com.abstractions.web;

import java.util.List;

/**
 * 
 * @author Martin Aparicio Pons (martin.aparicio.pons@gmail.com)
 */
public class AddConnectorForm {

  private String name;
  private String type;
  private List<String> configurationNames;
  private List<String> configurationValues;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getConfigurationNames() {
    return configurationNames;
  }

  public void setConfigurationNames(List<String> configurationNames) {
    this.configurationNames = configurationNames;
  }

  public List<String> getConfigurationValues() {
    return configurationValues;
  }

  public void setConfigurationValues(List<String> configurationValues) {
    this.configurationValues = configurationValues;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
