package com.abstractions.service;

import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Application;
import com.abstractions.repository.GenericRepository;

@Service
public class ApplicationService {

	private GenericRepository repository;
	
	protected ApplicationService() { }
	
	public ApplicationService(GenericRepository repository) {
		Validate.notNull(repository);
		
		this.repository = repository;
	}
	
	@Transactional
	public void addApplication(String name) {
		Application app = new Application(name);
		this.repository.save(app);
	}
	
	@Transactional
	public List<Application> getApplications() {
		return this.repository.get(Application.class, "name");
	}
}
