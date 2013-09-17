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
import com.abstractions.web.WebUserKeyProvider;
import com.service.core.ContextDefinition;
import com.service.core.DevelopmentContextHolder;
import com.service.core.NamesMapping;
import com.service.core.ServiceException;
import com.service.repository.ContextDefinitionMarshaller;
import com.service.repository.MarshallingException;

@Service
public class FlowService {

	private GenericRepository repository;
	private ApplicationService applicationService;
	private ContextDefinitionMarshaller marshaller;
	private DevelopmentContextHolder holder;
	
	protected FlowService() { }
	
	public FlowService(GenericRepository repository, ApplicationService applicationService, NamesMapping mapping, DevelopmentContextHolder holder) {
		Validate.notNull(repository);
		Validate.notNull(applicationService);
		Validate.notNull(mapping);
		Validate.notNull(holder);
		
		this.repository = repository;
		this.applicationService = applicationService;
		this.marshaller = new ContextDefinitionMarshaller(mapping);
		this.holder = holder;
		
		this.holder.setKeyProvider(new WebUserKeyProvider());
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
	public void addFlow(long applicationId, String name, ContextDefinition context) throws MarshallingException {
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
	public void editFlow(long teamId, long applicationId, long flowId, String name, ContextDefinition context) throws MarshallingException {
		String json = this.marshaller.marshall(context);
		
		Flow flow = this.getFlow(teamId, applicationId, flowId);
		flow.setJson(json);
		
		Application application = this.applicationService.getApplication(applicationId);
		application.addFlow(flow);
		
		this.repository.save(flow);
		this.repository.save(application);		
	}

	@Transactional
	public Flow loadFlow(long teamId, long applicationId, long flowId) {
		Flow flow = this.getFlow(teamId, applicationId, flowId);
		ContextDefinition context;
		
		try 
		{
			context = this.marshaller.unmarshall(flow.getJson());
			context.instantiate();
		} 
		catch (MarshallingException e) 
		{
			throw new IllegalArgumentException("Marshalling exception", e);
		} catch (ServiceException e) {
			throw new IllegalArgumentException("Error instantiating context", e);
		} 
		
		context.initialize();
		this.holder.put(context);
		
		return flow;
	}
}
