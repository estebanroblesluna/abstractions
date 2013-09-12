package com.abstractions.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Application;
import com.abstractions.model.Environment;
import com.abstractions.model.Property;
import com.abstractions.repository.GenericRepository;

@Service
public class PropertiesService {

	private GenericRepository repository;
	private ApplicationService applicationService;
	
	protected PropertiesService() { }
	
	public PropertiesService(GenericRepository repository, ApplicationService applicationService) {
		Validate.notNull(repository);
		Validate.notNull(applicationService);
		
		this.repository = repository;
		this.applicationService = applicationService;
	}
	
	@Transactional
	public void addProperty(long applicationId, String name, String value) {
		Application application = this.applicationService.getApplication(applicationId);
		Property property = new Property(name, value, Environment.DEV);
		
		application.addProperty(property);
		this.repository.save(property);
		this.repository.save(application);
	}
	
	@Transactional
	public List<Property> getProperties(long teamId, long applicationId) {
		Application application = this.applicationService.getApplication(applicationId);
		
		List<Property> properties = new ArrayList<Property>();
		properties.addAll(application.getProperties());
		return properties;
	}

	@Transactional
	public void removePropertiesByIds(Collection<Long> idsToRemove) {
		for (Long id : idsToRemove) {
			this.repository.delete(Property.class, id);
		}
	}
}
