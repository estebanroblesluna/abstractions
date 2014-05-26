package com.abstractions.service.core;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.model.Application;
import com.abstractions.model.Connector;
import com.abstractions.model.Flow;
import com.abstractions.model.Team;
import com.abstractions.repository.GenericRepository;
import com.abstractions.service.repository.CompositeTemplateMarshaller;
import com.abstractions.service.repository.MarshallingException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class DatabaseApplicationDefinitionLoader implements ApplicationDefinitionLoader {

	private static final Log log = LogFactory.getLog(DatabaseApplicationDefinitionLoader.class);
	
	private GenericRepository repository;
	private PropertiesLoader propertiesLoader;
	
  protected DatabaseApplicationDefinitionLoader() { }

  public DatabaseApplicationDefinitionLoader(GenericRepository repository, PropertiesLoader propertiesLoader) {
		Validate.notNull(repository);
		
		this.repository = repository;
    this.propertiesLoader = propertiesLoader;
	}
	
	@Override
	@Transactional
	public ApplicationDefinition load(long applicationId, NamesMapping mapping) {
		Application application = this.repository.get(Application.class, applicationId);

		ApplicationDefinition appDefinition = new ApplicationDefinition(application.getName(), mapping, this.propertiesLoader);
		appDefinition.setId(applicationId);

		Team team = application.getTeam();
		List<Connector> connectors = team.getConnectors();
		for (Connector connector : connectors) {
		  String id = connector.getName();
		  ElementDefinition metaElementDefinition = mapping.getDefinition(connector.getType());
		  ElementTemplate connectorTemplate = new ElementTemplate(id, metaElementDefinition);
		  connectorTemplate.setProperties(connector.getConfigurations());
		  
		  appDefinition.addDefinition(connectorTemplate);
		}

		
		CompositeTemplateMarshaller marshaller = new CompositeTemplateMarshaller(appDefinition.getMapping());

		for (Flow flow : application.getFlows()) {
			try {
				CompositeTemplate flowTemplate = marshaller.unmarshall(appDefinition, flow.getJson());
				appDefinition.addDefinition(flowTemplate);
			} catch (MarshallingException e) {
				log.error("Error unmarshalling flow", e);
			}
		}
		
		return appDefinition;
	}
}
