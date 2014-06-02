package com.abstractions.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.model.Connector;
import com.abstractions.repository.GenericRepository;
import com.abstractions.service.core.DevelopmentContextHolder;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.ElementTemplate;
import com.abstractions.utils.LiquidMLUtils;

public class DevelopmentEnvironmentService {

  private static final Log log = LogFactory.getLog(DevelopmentEnvironmentService.class);
  
  private DevelopmentContextHolder holder;
  private GenericRepository repository;
  
  protected DevelopmentEnvironmentService() { }

  public DevelopmentEnvironmentService(DevelopmentContextHolder holder, GenericRepository repository) {
    Validate.notNull(holder);
    Validate.notNull(repository);
    
    this.holder = holder;
    this.repository = repository;
  }
  
  @Transactional
  public void addConnector(Long applicationId, Long connectorId, Connector connector, String connectorName) {
    List<Map<String, Object>> apps = this.holder.getApplicationMaps(applicationId);

    for (Map<String, Object> map : apps) {
      ApplicationTemplate appTemplate = (ApplicationTemplate) map.get(DevelopmentContextHolder.APP_TEMPLATE);
      ApplicationDefinition appDefinition = (ApplicationDefinition) map.get(DevelopmentContextHolder.APP_DEFINITION);

      ElementTemplate existingConnectorTemplate = appTemplate.getDefinition(connectorId.toString());
      
      if (existingConnectorTemplate != null) { //UPDATE THE EXISTING ONE
        LiquidMLUtils.stop(existingConnectorTemplate.getInstance());
        LiquidMLUtils.terminate(existingConnectorTemplate.getInstance());

        // OVERRIDE all properties
        existingConnectorTemplate.overrideObject(null);
        existingConnectorTemplate.setProperties(connector.getConfigurations());
      } else { //ADD THE NEW ONE
        String id = connector.getName();
        NamesMapping mapping = appDefinition.getMapping();
        ElementDefinition metaElementDefinition = mapping.getDefinition(connector.getType());
        ElementTemplate connectorTemplate = new ElementTemplate(id, metaElementDefinition);
        connectorTemplate.setProperties(connector.getConfigurations());
        
        appDefinition.addDefinition(connectorTemplate);
        appTemplate.addDefinition(connectorTemplate);
        
        try {
          appDefinition.instantiate(appTemplate.getCompositeElement(), mapping, appTemplate, appTemplate);
        } catch (InstantiationException e) {
          log.error("Error initializing connector", e);
        } catch (IllegalAccessException e) {
          log.error("Error initializing connector", e);
        }
      }

      // SYNC
      try {
        appTemplate.sync();
      } catch (ServiceException e) {
        log.error("Error updating connector", e);
      }
    }
  }
  
  @Transactional
  public void removeConnector(Long applicationId, Long connectorId, Connector connector, String connectorName) {
    List<Map<String, Object>> apps = this.holder.getApplicationMaps(applicationId);

    for (Map<String, Object> map : apps) {
      ApplicationTemplate appTemplate = (ApplicationTemplate) map.get(DevelopmentContextHolder.APP_TEMPLATE);
      ApplicationDefinition appDefinition = (ApplicationDefinition) map.get(DevelopmentContextHolder.APP_DEFINITION);
      
      appDefinition.removeDefinition(connectorName);
      appTemplate.deleteDefinition(connectorName);

      // SYNC
      try {
        appTemplate.sync();
      } catch (ServiceException e) {
        log.error("Error updating connector", e);
      }
    }
  }
}
