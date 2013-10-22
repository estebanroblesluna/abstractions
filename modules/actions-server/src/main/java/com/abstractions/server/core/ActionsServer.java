package com.abstractions.server.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

import com.abstractions.api.Processor;
import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.instance.common.LogProcessorWrapper;
import com.abstractions.instance.common.PerformanceProcessor;
import com.abstractions.instance.common.ProcessorWrapper;
import com.abstractions.service.core.ContextDefinition;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.service.repository.ContextDefinitionMarshaller;
import com.abstractions.service.repository.MarshallingException;

public class ActionsServer {

	private static Log log = LogFactory.getLog(ActionsServer.class);

	private final ContextDefinitionMarshaller marshaller;
	private final Map<String, ContextDefinition> definitions;
	
	private final Map<String, List<PerformanceProcessor>> profilers;
	private final Map<String, List<LogProcessorWrapper>> logs;
	private final Map<String, LogProcessorWrapper> objectIdLogs;
	private final Map<String, StatisticsInterpreterDelegate> statistics;
	private final Map<Object, String> wrapperToObjectIdMapping;
	
	private final NamesMapping mapping;
	
	private final String applicationDirectory;
	
	public ActionsServer(String applicationDirectory, NamesMapping mapping) {
		Validate.notNull(mapping);
		Validate.notNull(applicationDirectory);

		this.mapping = mapping;
		this.applicationDirectory = applicationDirectory;
		
		this.marshaller = new ContextDefinitionMarshaller(mapping);
		this.definitions = new ConcurrentHashMap<String, ContextDefinition>();
		this.profilers = new ConcurrentHashMap<String, List<PerformanceProcessor>>();
		this.objectIdLogs = new ConcurrentHashMap<String, LogProcessorWrapper>();
		this.logs = new ConcurrentHashMap<String, List<LogProcessorWrapper>>();
		this.statistics = new ConcurrentHashMap<String, StatisticsInterpreterDelegate>();
		this.wrapperToObjectIdMapping = new ConcurrentHashMap<Object, String>();
		
		this.startApplications();
 	}

	private void startApplications() {
		File applicationDirectory = new File(this.applicationDirectory + "/");
		for (Object fileAsObject : applicationDirectory.listFiles()) {
			if (fileAsObject instanceof File && ((File) fileAsObject).isDirectory()) {
				File file = (File) fileAsObject;
				this.startFromFiles(file.getName());
			}
		}
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
	private void start(String contextDefinition) {
		try {
			ContextDefinition definition = marshaller.unmarshall(contextDefinition);
			StatisticsInterpreterDelegate statistics = new StatisticsInterpreterDelegate();
			definition.setDefaultInterpreterDelegate(statistics);
			
			this.statistics.put(definition.getId(), statistics);
			
			definition.sync();
			definition.start();
			
			this.definitions.put(definition.getId(), definition);
		} catch (MarshallingException e) {
			log.error("Error reading definition", e);
		} catch (ServiceException e) {
			log.error("Error syncing definition", e);
		}
	}

	public void start(String applicationId, InputStream applicationZip) {
		ZipInputStream zipInputStream = new ZipInputStream(applicationZip);
		
		this.cleanUpApplication(applicationId);
		
		try {
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			while (zipEntry != null) {
				this.saveFile(applicationId, zipEntry, zipInputStream);
				zipInputStream.closeEntry();
				zipEntry = zipInputStream.getNextEntry();
			}
			zipInputStream.close();
		} catch (IOException e) {
			log.error("Error deploying application", e);
		} finally {
			IOUtils.closeQuietly(applicationZip);
			IOUtils.closeQuietly(zipInputStream);
		}
		
		this.startFromFiles(applicationId);
	}

	private void saveFile(String applicationId, ZipEntry zipEntry, ZipInputStream zipInputStream) throws IOException {
		File file = new File(this.applicationDirectory + "/" + applicationId + "/" + zipEntry.getName());
		FileUtils.writeByteArrayToFile(file, IOUtils.toByteArray(zipInputStream));
	}

	private void startFromFiles(String applicationId) {
		File applicationDirectory = new File(this.applicationDirectory + "/" + applicationId);
		if (applicationDirectory.exists()) {
			File flowDirectory = new File(applicationDirectory, "flows");
			for (File flowFile : flowDirectory.listFiles()) {
				this.startFlow(flowFile);
			}
		}
	}

	private void startFlow(File flowFile) {
		try {
			String flowDefinition = FileUtils.readFileToString(flowFile);
			this.start(flowDefinition);
		} catch (IOException e) {
			log.warn("Error reading flow (ignoring for now) " + flowFile.getAbsolutePath(), e);
		}
	}

	private void cleanUpApplication(String applicationId) {
		File applicationDirectory = new File(this.applicationDirectory + "/" + applicationId);
		if (applicationDirectory.exists()) {
			try {
				FileUtils.deleteDirectory(applicationDirectory);
			} catch (IOException e) {
				log.warn("Error deleting application " + applicationId, e);
			}
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
		ObjectClazz objectDefinition = contextDefinition.getDefinition(objectId);
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
		ObjectClazz objectDefinition = contextDefinition.getDefinition(objectId);
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
		ObjectClazz objectDefinition = contextDefinition.getDefinition(objectId);
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
		ObjectClazz objectDefinition = contextDefinition.getDefinition(objectId);
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

		ContextDefinition context = this.definitions.get(contextId);
		LazyComputedCacheTransformation transformation = new LazyComputedCacheTransformation(
				objectId, memcachedURL, keyExpression, cacheExpressions, ttl, this.mapping);
		
		transformation.transform(context);
	}
	
	public void addLazyAutorefreshableCache(
			String contextId, 
			String objectId,
			String memcachedURL, 
			String keyExpression, 
			String cacheExpressions,
			int ttl) {

		ContextDefinition context = this.definitions.get(contextId);
		LazyAutorefreshableCacheTransformation transformation = new LazyAutorefreshableCacheTransformation(
				objectId, memcachedURL, keyExpression, cacheExpressions, ttl, this.mapping);
		
		transformation.transform(context);

	}

	public Map<String, StatisticsInfo> getStatistics() {
		Map<String, StatisticsInfo> stats = new HashMap<String, StatisticsInfo>();
		
		for (Map.Entry<String, StatisticsInterpreterDelegate> entry : this.statistics.entrySet()) {
			stats.put(entry.getKey(), entry.getValue().reset());
		}
		
		return stats;
	}
}
