package com.abstractions.service.core;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.model.Application;
import com.abstractions.model.Flow;
import com.abstractions.repository.GenericRepository;
import com.abstractions.service.repository.CompositeTemplateMarshaller;
import com.abstractions.service.repository.MarshallingException;
import com.abstractions.template.CompositeTemplate;

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
