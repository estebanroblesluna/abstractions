package com.server.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

import com.core.api.Processor;
import com.core.common.LogProcessorWrapper;
import com.core.common.PerformanceProcessor;
import com.core.common.ProcessorWrapper;
import com.core.impl.ConnectionType;
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
	private final Map<String, List<LogProcessorWrapper>> logs;
	private final Map<String, LogProcessorWrapper> objectIdLogs;
	private final Map<Object, String> wrapperToObjectIdMapping;
	
	private final NamesMapping mapping;
	
	public ActionsServer(NamesMapping mapping) {
		Validate.notNull(mapping);
		
		this.marshaller = new ContextDefinitionMarshaller(mapping);
		this.definitions = new ConcurrentHashMap<String, ContextDefinition>();
		this.profilers = new ConcurrentHashMap<String, List<PerformanceProcessor>>();
		this.objectIdLogs = new ConcurrentHashMap<String, LogProcessorWrapper>();
		this.logs = new ConcurrentHashMap<String, List<LogProcessorWrapper>>();
		this.mapping = mapping;
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
		if (!(object instanceof ProcessorWrapper) && (object instanceof Processor)  
				|| (object instanceof ProcessorWrapper && !((ProcessorWrapper) object).isWrapWith(PerformanceProcessor.class))) {
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
		if (object instanceof ProcessorWrapper && ((ProcessorWrapper) object).isWrapWith(PerformanceProcessor.class)) {
			ProcessorWrapper wrapper = (ProcessorWrapper) object;
			Object processor = wrapper.unwrap(PerformanceProcessor.class);
			objectDefinition.overrideObject(processor);
			this.getProfilers(contextId).remove(wrapper);
			this.wrapperToObjectIdMapping.remove(wrapper);
		}
	}

	
	public void addLogger(String contextId, String objectId, String beforeExpression, String afterExpression) {
		ContextDefinition contextDefinition = this.definitions.get(contextId);
		ObjectDefinition objectDefinition = contextDefinition.getDefinition(objectId);
		Object object = objectDefinition.getInstance();
		if (!(object instanceof ProcessorWrapper) && (object instanceof Processor) 
				|| (object instanceof ProcessorWrapper && !((ProcessorWrapper) object).isWrapWith(LogProcessorWrapper.class))) {
			Processor processor = (Processor) object;
			LogProcessorWrapper wrapper = new LogProcessorWrapper(processor);
			wrapper.setBeforeExpression(beforeExpression);
			wrapper.setAfterExpression(afterExpression);
			
			objectDefinition.overrideObject(wrapper);
			this.getLoggers(contextId).add(wrapper);
			this.wrapperToObjectIdMapping.put(wrapper, objectId);
			this.objectIdLogs.put(objectId, wrapper);
		}
	}
	
	public void removeLogger(String contextId, String objectId) {
		ContextDefinition contextDefinition = this.definitions.get(contextId);
		ObjectDefinition objectDefinition = contextDefinition.getDefinition(objectId);
		Object object = objectDefinition.getInstance();
		if (object instanceof ProcessorWrapper && ((ProcessorWrapper) object).isWrapWith(LogProcessorWrapper.class)) {
			ProcessorWrapper wrapper = (ProcessorWrapper) object;
			Object processor = wrapper.unwrap(LogProcessorWrapper.class);
			objectDefinition.overrideObject(processor);
			this.getLoggers(contextId).remove(wrapper);
			this.wrapperToObjectIdMapping.remove(wrapper);
			this.objectIdLogs.remove(objectId);
		}
	}
	
	private List<LogProcessorWrapper> getLoggers(String contextId) {
		if (!this.logs.containsKey(contextId)) {
			synchronized (this.logs) {
				if (!this.logs.containsKey(contextId)) {
					this.logs.put(contextId, new ArrayList<LogProcessorWrapper>());
				}
			}
		}
		
		return this.logs.get(contextId);
	}

	public LogInfo getLogInfo(String contextId) {
		List<LogProcessorWrapper> logs = this.getLoggers(contextId);
		LogInfo info = new LogInfo();
		for (LogProcessorWrapper log : logs) {
			List<String> lines = log.lines();
			info.addLogLines(this.wrapperToObjectIdMapping.get(log), lines);
		}
		return info;
	}

	public List<String> getLogLines(String contextId, String objectId) {
		LogProcessorWrapper wrapper = this.objectIdLogs.get(objectId);
		if (wrapper == null) {
			return new ArrayList<String>();
		}
		List<String> lines = wrapper.lines();
		return lines == null ? new ArrayList<String>() : lines;
	}

	public void addLazyComputedCache(
			String contextId, 
			String objectId,
			String memcachedURL, 
			String keyExpression, 
			String cacheExpressions,
			int ttl) {

		//OBTAIN THE CONNECTION DEFINITION
		ContextDefinition context = this.definitions.get(contextId);
		ObjectDefinition connectionDefinition = context.getDefinition(objectId);
		
		//SAVE THE PREVIOUS TARGET ID
		String previousTargetId = connectionDefinition.getProperty("target");
		
		//CREATE CACHE, CHOICE AND CHAIN
		ObjectDefinition getCacheDefinition = new ObjectDefinition(this.mapping.getDefinition("GET_MEMCACHED"));
		ObjectDefinition choiceDefinition = new ObjectDefinition(this.mapping.getDefinition("CHOICE"));
		ObjectDefinition chainDefinition = new ObjectDefinition(this.mapping.getDefinition("CHAIN"));
		context.addDefinition(getCacheDefinition);
		context.addDefinition(choiceDefinition);
		context.addDefinition(chainDefinition);
		
		//SET THE EXPRESSION TO GET FROM MEMCACHED
		String adaptedKeyExpression = "'__CACHED_" + getCacheDefinition.getId() + "_' + " + keyExpression;
		getCacheDefinition.setProperty("expression", adaptedKeyExpression);
		
		//CACHE -> CHOICE
		context.addConnection(getCacheDefinition.getId(), choiceDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);

		//CHOICE -> CHAIN
		String choiceConnectionId = context.addConnection(choiceDefinition.getId(), chainDefinition.getId(), ConnectionType.CHOICE_CONNECTION);
		//IF CACHE IS NULL
		ObjectDefinition choiceConnectionDefinition = context.getDefinition(choiceConnectionId);
		choiceConnectionDefinition.setProperty("expression", "message.payload == null");

		//POINT TO THE PREVIOUS COMPUTATION
		context.addConnection(chainDefinition.getId(), previousTargetId.substring(4), ConnectionType.CHAIN_CONNECTION);
		
		//ADD PUT OPERATION
		String[] putExpressions = StringUtils.split(cacheExpressions, ';');
		if (putExpressions != null && putExpressions.length >= 1) {
			ObjectDefinition nullProcessorDefinition = new ObjectDefinition(this.mapping.getDefinition("NULL"));
			
			ObjectDefinition putCacheDefinition = new ObjectDefinition(this.mapping.getDefinition("PUT_MEMCACHED"));
			putCacheDefinition.setProperty("keyExpression", adaptedKeyExpression);
			putCacheDefinition.setProperty("valueExpression", putExpressions[0]);

			context.addDefinition(nullProcessorDefinition);
			context.addDefinition(putCacheDefinition);
			context.addConnection(chainDefinition.getId(), nullProcessorDefinition.getId(), ConnectionType.CHAIN_CONNECTION);
			context.addConnection(nullProcessorDefinition.getId(), putCacheDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		}
		
		//FINALLY CHANGE THE CONNECTION TO POINT TO THE GET FROM CACHE
		connectionDefinition.setProperty("target", "urn:" + getCacheDefinition.getId());
		
		try {
			context.sync();
		} catch (ServiceException e) {
			log.warn("Error syncing context", e);
		}
	}
	
	public void addLazyAutorefreshableCache(
			String contextId, 
			String objectId,
			String memcachedURL, 
			String keyExpression, 
			String cacheExpressions,
			int ttl) {

		//OBTAIN THE CONNECTION DEFINITION
		ContextDefinition context = this.definitions.get(contextId);
		ObjectDefinition connectionDefinition = context.getDefinition(objectId);
		
		//SAVE THE PREVIOUS TARGET ID
		String previousTargetId = connectionDefinition.getProperty("target");
		
		//CREATE CACHE, CHOICE, CHAIN, WIRE_TAP
		ObjectDefinition getCacheDefinition = new ObjectDefinition(this.mapping.getDefinition("GET_MEMCACHED"));
		ObjectDefinition choiceDefinition = new ObjectDefinition(this.mapping.getDefinition("CHOICE"));
		ObjectDefinition chainDefinition = new ObjectDefinition(this.mapping.getDefinition("CHAIN"));
		ObjectDefinition wireTapDefinition = new ObjectDefinition(this.mapping.getDefinition("WIRE_TAP"));
		context.addDefinition(getCacheDefinition);
		context.addDefinition(choiceDefinition);
		context.addDefinition(chainDefinition);
		context.addDefinition(wireTapDefinition);
		
		//SET THE EXPRESSION TO GET FROM MEMCACHED
		String adaptedKeyExpression = "'__CACHED_" + getCacheDefinition.getId() + "_' + " + keyExpression;
		String adaptedTimeKeyExpression = "'__CACHED_" + getCacheDefinition.getId() + "_time_' + " + keyExpression;
		getCacheDefinition.setProperty("expression", adaptedKeyExpression);
		
		//CACHE -> CHOICE
		context.addConnection(getCacheDefinition.getId(), choiceDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);

		//CHOICE -> CHAIN
		String choiceConnectionId = context.addConnection(choiceDefinition.getId(), chainDefinition.getId(), ConnectionType.CHOICE_CONNECTION);
		//IF CACHE IS NULL
		ObjectDefinition choiceConnectionDefinition = context.getDefinition(choiceConnectionId);
		choiceConnectionDefinition.setProperty("expression", "message.payload == null");

		//CHOICE -> WIRE_TAP
		String choiceWireTapConnectionId = context.addConnection(choiceDefinition.getId(), wireTapDefinition.getId(), ConnectionType.CHOICE_CONNECTION);
		ObjectDefinition choiceWireTapConnectionDefinition = context.getDefinition(choiceWireTapConnectionId);
		choiceWireTapConnectionDefinition.setProperty("expression", "message.payload != null");

		//POINT TO THE PREVIOUS COMPUTATION
		context.addConnection(chainDefinition.getId(), previousTargetId.substring(4), ConnectionType.CHAIN_CONNECTION);
		
		//ADD PUT OPERATION
		String[] putExpressions = StringUtils.split(cacheExpressions, ';');
		if (putExpressions != null && putExpressions.length >= 1) {
			ObjectDefinition nullProcessorDefinition = new ObjectDefinition(this.mapping.getDefinition("NULL"));
			
			ObjectDefinition putCacheDefinition = new ObjectDefinition(this.mapping.getDefinition("PUT_MEMCACHED"));
			putCacheDefinition.setProperty("keyExpression", adaptedKeyExpression);
			putCacheDefinition.setProperty("valueExpression", putExpressions[0]);

			ObjectDefinition putCacheTimeDefinition = new ObjectDefinition(this.mapping.getDefinition("PUT_MEMCACHED"));
			putCacheTimeDefinition.setProperty("keyExpression", adaptedTimeKeyExpression);
			putCacheTimeDefinition.setProperty("valueExpression", "new java.util.Date().getTime()");

			context.addDefinition(nullProcessorDefinition);
			context.addDefinition(putCacheDefinition);
			context.addDefinition(putCacheTimeDefinition);
			
			context.addConnection(chainDefinition.getId(), nullProcessorDefinition.getId(), ConnectionType.CHAIN_CONNECTION);
			context.addConnection(nullProcessorDefinition.getId(), putCacheDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
			context.addConnection(putCacheDefinition.getId(), putCacheTimeDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		}
		
		//ADD WIRE TAP PART
		ObjectDefinition getTimeFromCacheDefinition = new ObjectDefinition(this.mapping.getDefinition("GET_MEMCACHED"));
		ObjectDefinition timeChoiceDefinition = new ObjectDefinition(this.mapping.getDefinition("CHOICE"));
		context.addDefinition(getTimeFromCacheDefinition);
		context.addDefinition(timeChoiceDefinition);
		getTimeFromCacheDefinition.setProperty("expression", adaptedTimeKeyExpression);

		//WIRE_TAP -> CACHE
		context.addConnection(wireTapDefinition.getId(), getTimeFromCacheDefinition.getId(), ConnectionType.WIRE_TAP_CONNECTION);
		//CACHE -> CHOICE
		context.addConnection(getTimeFromCacheDefinition.getId(), timeChoiceDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		//CHOICE -> CHAIN
		String timeChoiceId = context.addConnection(timeChoiceDefinition.getId(), chainDefinition.getId(), ConnectionType.CHOICE_CONNECTION);
		
		//TIME CHOICE CONDITION
		ObjectDefinition timeChoiceConnectionDefinition = context.getDefinition(timeChoiceId);
		timeChoiceConnectionDefinition.setProperty("expression", "(message.payload != null) && ((new java.util.Date().getTime() - message.payload) > 3000)");
	
		
		//FINALLY CHANGE THE CONNECTION TO POINT TO THE GET FROM CACHE
		connectionDefinition.setProperty("target", "urn:" + getCacheDefinition.getId());
		
		try {
			context.sync();
		} catch (ServiceException e) {
			log.warn("Error syncing context", e);
		}
	}
}
