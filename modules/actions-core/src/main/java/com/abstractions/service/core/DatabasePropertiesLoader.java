package com.abstractions.service.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Environment;
import com.abstractions.model.Property;
import com.abstractions.repository.GenericRepository;

public class DatabasePropertiesLoader implements PropertiesLoader {

  private GenericRepository repository;

  protected DatabasePropertiesLoader() { }

  public DatabasePropertiesLoader(GenericRepository repository) {
    Validate.notNull(repository);
    
    this.repository = repository;
  }
  
  @SuppressWarnings("unchecked")
  @Transactional
  @Override
  public Map<String, String> loadPropertiesOf(long applicationId) {
    List<Property> properties = this.repository.getProperties(applicationId, Environment.DEV);
    
    Map<String, String> propertiesMap = new HashMap<String, String>();
    for (Property property : properties) {
      propertiesMap.put(property.getName(), property.getValue());
    }
    
    return propertiesMap;
  }
}
