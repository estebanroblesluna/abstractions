package com.server.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

import com.core.api.Processor;
import com.core.common.PerformanceProcessor;
import com.service.core.ContextDefinition;
import com.service.core.NamesMapping;
import com.service.core.ObjectDefinition;
import com.service.core.ServiceException;
import com.service.repository.ContextDefinitionMarshaller;
import com.service.repository.MarshallingException;

public class ActionsServer {

	private static Log log = LogFactory.getLog(ActionsServer.class);

	private final ContextDefinitionMarshaller marshaller;
	private final Map<String, ContextDefinition> definitions;
	
	private final Map<String, List<PerformanceProcessor>> profilers;
	private final Map<Object, String> wrapperToObjectIdMapping;
	
	public ActionsServer(NamesMapping mapping) {
		Validate.notNull(mapping);
		
		this.marshaller = new ContextDefinitionMarshaller(mapping);
		this.definitions = new ConcurrentHashMap<String, ContextDefinition>();
		this.profilers = new ConcurrentHashMap<String, List<PerformanceProcessor>>();
		this.wrapperToObjectIdMapping = new ConcurrentHashMap<Object, String>();
 	}

	/**
	 * Starts the context definition in contextDefinition assuming that it 
	 * has been stored as a json object.
	 * The it tries to sync and start the definition.
	 * 
	 * If the definition has been started you can stop it at any time by calling the stop method
	 * 
	 * @param contextDefinition
	 */
	public void start(String contextDefinition) {
		try {
			ContextDefinition definition = marshaller.unmarshall(contextDefinition);
			definition.sync();
			definition.start();
			
			this.definitions.put(definition.getId(), definition);
		} catch (MarshallingException e) {
			log.error("Error reading definition", e);
		} catch (ServiceException e) {
			log.error("Error syncing definition", e);
		}
	}
	
	/**
	 * Stops the context definition under contextId if it has been started
	 * 
	 * @param definitionId
	 */
	public void stop(String contextId) {
		ContextDefinition definition = this.definitions.remove(contextId);
		
		if (definition != null) {
			definition.terminate();
		}
	}

	/**
	 * Returns whether the context id is running in this server
	 * 
	 * @param contextId the id of the context
	 * @return whether it is running or not
	 */
	public boolean isRunning(String contextId) {
		ContextDefinition definition = this.definitions.get(contextId);
		return definition != null;
	}
	
	public ProfilingInfo getProfilingInfo(String contextId) {
		ProfilingInfo info = new ProfilingInfo();
		
		List<PerformanceProcessor> profilers = this.getProfilers(contextId);
		for (PerformanceProcessor profiler : profilers) {
			info.addAverage(this.wrapperToObjectIdMapping.get(profiler), profiler.getAverage());
		}
		return info;
	}

	private List<PerformanceProcessor> getProfilers(String contextId) {
		if (!this.profilers.containsKey(contextId)) {
			synchronized (this.profilers) {
				if (!this.profilers.containsKey(contextId)) {
					this.profilers.put(contextId, new ArrayList<PerformanceProcessor>());
				}
			}
		}
		
		return this.profilers.get(contextId);
	}

	public void addProfiler(String contextId, String objectId) {
		ContextDefinition contextDefinition = this.definitions.get(contextId);
		ObjectDefinition objectDefinition = contextDefinition.getDefinition(objectId);
		Object object = objectDefinition.getInstance();
		if (object instanceof Processor && !(object instanceof PerformanceProcessor)) {
			Processor processor = (Processor) object;
			PerformanceProcessor wrapper = new PerformanceProcessor(processor);
			objectDefinition.overrideObject(wrapper);
			this.getProfilers(contextId).add(wrapper);
			this.wrapperToObjectIdMapping.put(wrapper, objectId);
		}
	}

	public void removeProfiler(String contextId, String objectId) {
		ContextDefinition contextDefinition = this.definitions.get(contextId);
		ObjectDefinition objectDefinition = contextDefinition.getDefinition(objectId);
		Object object = objectDefinition.getInstance();
		if (object instanceof PerformanceProcessor) {
			PerformanceProcessor wrapper = (PerformanceProcessor) object;
			Object processor = wrapper.getInstance();
			objectDefinition.overrideObject(processor);
			this.getProfilers(contextId).remove(wrapper);
			this.wrapperToObjectIdMapping.remove(wrapper);
		}
	}

	public void getLoggerLines(String contextId, String objectId, String logName, Integer from, Integer to) {
		// TODO Auto-generated method stub
	}
	
	public void getLoggers(String contextId) {
		// TODO Auto-generated method stub
	}

	public void addLogger(String contextId, String objectId, String logName, String logExpression) {
		// TODO Auto-generated method stub
	}
	
	public void removeLogger(String contextId, String objectId, String logName) {
		// TODO Auto-generated method stub
	}
}
