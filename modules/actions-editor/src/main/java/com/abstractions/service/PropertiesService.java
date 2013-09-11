package com.abstractions.service;

import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Environment;
import com.abstractions.model.Property;
import com.abstractions.model.Server;
import com.abstractions.repository.GenericRepository;

@Service
public class PropertiesService {

	private GenericRepository repository;
	
	protected PropertiesService() { }
	
	public PropertiesService(GenericRepository repository) {
		Validate.notNull(repository);
		
		this.repository = repository;
	}
	
	@Transactional
	public void addProperty(String name, String value) {
		Property property = new Property(name, value, Environment.DEV);
		this.repository.save(property);
	}
	
	@Transactional
	public List<Property> getProperties() {
		return this.repository.get(Property.class, "name");
	}
}
