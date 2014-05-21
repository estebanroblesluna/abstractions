package com.abstractions.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.model.Application;
import com.abstractions.model.Flow;
import com.abstractions.model.User;
import com.abstractions.repository.GenericRepository;
import com.abstractions.service.core.DevelopmentContextHolder;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.service.repository.CompositeTemplateMarshaller;
import com.abstractions.service.repository.MarshallingException;
import com.abstractions.template.CompositeTemplate;

@Service
public class FlowService {

	private GenericRepository repository;
	private ApplicationService applicationService;
	private CompositeTemplateMarshaller marshaller;
	private DevelopmentContextHolder holder;
	
	protected FlowService() { }
	
	public FlowService(GenericRepository repository, ApplicationService applicationService, NamesMapping mapping, DevelopmentContextHolder holder) {
		Validate.notNull(repository);
		Validate.notNull(applicationService);
		Validate.notNull(mapping);
		Validate.notNull(holder);
		
		this.repository = repository;
		this.applicationService = applicationService;
		this.marshaller = new CompositeTemplateMarshaller(mapping);
		this.holder = holder;
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
	public void addFlow(long applicationId, String name, CompositeTemplate context) throws MarshallingException {
		String json = this.marshaller.marshall(context);
		
		Flow flow = new Flow(name);
		flow.setJson(json);
		
		Application application = this.applicationService.getApplication(applicationId);
		application.addFlow(flow);
		
		this.repository.save(flow);
		this.repository.save(application);
	}
	
	@Transactional
	public Flow getFlow(long teamId, long applicationId, long flowId) {
		return this.repository.get(Flow.class, flowId);
	}

	@Transactional
	public void editFlow(long teamId, long applicationId, long flowId, String name, CompositeTemplate context) throws MarshallingException {
		String json = this.marshaller.marshall(context);
		
		Flow flow = this.getFlow(teamId, applicationId, flowId);
		flow.setJson(json);
		
		Application application = this.applicationService.getApplication(applicationId);
		application.addFlow(flow);
		
		this.repository.save(flow);
		this.repository.save(application);		
	}

	@Transactional
	public Flow loadFlow(User user, long teamId, long applicationId, long flowId) {
		Flow flow = this.getFlow(teamId, applicationId, flowId);
		
		try {
	    ApplicationTemplate appTemplate = this.holder.getApplicationTemplate(user, applicationId);
	    appTemplate.sync();
		} catch (ServiceException e) {
			throw new IllegalArgumentException("Error instantiating context", e);
		} 
		
		return flow;
	}
}
