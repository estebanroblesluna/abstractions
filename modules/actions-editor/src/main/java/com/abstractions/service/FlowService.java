package com.abstractions.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Application;
import com.abstractions.model.Flow;
import com.abstractions.repository.GenericRepository;

@Service
public class FlowService {

	private GenericRepository repository;
	private ApplicationService applicationService;
	
	protected FlowService() { }
	
	public FlowService(GenericRepository repository, ApplicationService applicationService) {
		Validate.notNull(repository);
		Validate.notNull(applicationService);
		
		this.repository = repository;
		this.applicationService = applicationService;
	}
	
	@Transactional
	public List<Flow> getFlows(long teamId, long applicationId) {
		Application application = this.applicationService.getApplication(applicationId);

		List<Flow> flows = new ArrayList<Flow>();
		flows.addAll(application.getFlows());
		return flows;
	}

	@Transactional
	public void removeFlowsByIds(long applicationId, Collection<Long> idsToRemove) {
		for (Long id : idsToRemove) {
			this.repository.delete(Flow.class, id);
		}		
	}

	@Transactional
	public void addFlow(long applicationId, String name, String json) {
		// TODO Auto-generated method stub
	}
}
