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
import com.abstractions.instance.common.LogProcessorWrapper;
import com.abstractions.instance.common.PerformanceProcessor;
import com.abstractions.instance.common.ProcessorWrapper;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ResourceService;
import com.abstractions.service.core.ServiceException;
import com.abstractions.service.repository.CompositeTemplateMarshaller;
import com.abstractions.service.repository.MarshallingException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class ActionsServer {

	private static Log log = LogFactory.getLog(ActionsServer.class);

	private final CompositeTemplateMarshaller marshaller;
	private final Map<String, CompositeTemplate> definitions;
	
	private final Map<String, List<PerformanceProcessor>> profilers;
	private final Map<String, List<LogProcessorWrapper>> logs;
	private final Map<String, LogProcessorWrapper> objectIdLogs;
	private final Map<String, StatisticsInterpreterDelegate> statistics;
	private final Map<Object, String> wrapperToObjectIdMapping;
	
	private final NamesMapping mapping;
	
	private final String applicationDirectory;

	private ResourceService resourceService;
	
	public ActionsServer(String applicationDirectory, NamesMapping mapping, ResourceService resourceService) {
		Validate.notNull(mapping);
		Validate.notNull(applicationDirectory);
		Validate.notNull(resourceService);

		this.mapping = mapping;
		this.applicationDirectory = applicationDirectory;
		this.resourceService = resourceService;
		
		this.marshaller = new CompositeTemplateMarshaller(mapping);
		this.definitions = new ConcurrentHashMap<String, CompositeTemplate>();
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
				try {
					this.startApplicationFromFiles(file.getName());
				} catch (Exception e) {
					log.error("Error starting application " + file.getName());
				}
			}
		}
	}

	/**
	 * Starts the context definition in CompositeTemplate assuming that it 
	 * has been stored as a json object.
	 * The it tries to sync and start the definition.
	 * 
	 * If the definition has been started you can stop it at any time by calling the stop method
	 * 
	 * @param appDefinition
	 * @param flowDefinition 
	 */
	private void start(ApplicationDefinition appDefinition, String flowDefinition) {
		try {
			CompositeTemplate composite = marshaller.unmarshall(appDefinition, flowDefinition);
			
			for (ElementTemplate template : composite.getDefinitions().values()) {
				appDefinition.addDefinition(template);
			}
		} catch (MarshallingException e) {
			log.error("Error reading definition", e);
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
			this.startApplicationFromFiles(applicationId);
		} catch (Exception e) {
			log.error("Error deploying application", e);
		} finally {
			IOUtils.closeQuietly(applicationZip);
			IOUtils.closeQuietly(zipInputStream);
		}
	}

	private void saveFile(String applicationId, ZipEntry zipEntry, ZipInputStream zipInputStream) throws IOException {
		// TODO use the save method in the ResourceService
		File file = new File(this.applicationDirectory + "/" + applicationId + "/" + zipEntry.getName());
		FileUtils.writeByteArrayToFile(file, IOUtils.toByteArray(zipInputStream));
	}

	private void startApplicationFromFiles(String applicationId) throws InstantiationException, IllegalAccessException, ServiceException {
		ApplicationDefinition appDefinition = new ApplicationDefinition(applicationId);
		File applicationDirectory = new File(this.applicationDirectory + "/" + applicationId);

		if (applicationDirectory.exists()) {
			File flowDirectory = new File(applicationDirectory, "flows");
			for (File flowFile : flowDirectory.listFiles()) {
				this.startFlow(appDefinition, flowFile);
			}
		}
		
		CompositeTemplate composite = appDefinition.createTemplate(this.mapping);
		composite.sync(null, this.mapping);
		composite.start();
		
		StatisticsInterpreterDelegate statistics = new StatisticsInterpreterDelegate();
		appDefinition.setDefaultInterpreterDelegate(statistics);
		
		this.statistics.put(applicationId, statistics);
		this.definitions.put(applicationId, composite);
	}

	private void startFlow(ApplicationDefinition appDefinition, File flowFile) {
		try {
			String flowDefinition = FileUtils.readFileToString(flowFile);
			this.start(appDefinition, flowDefinition);
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
	 * Stops the application definition under applicationId if it has been started
	 * 
	 * @param definitionId
	 */
	public void stop(String applicationId) {
		CompositeTemplate definition = this.definitions.remove(applicationId);
		
		if (definition != null) {
			definition.terminate();
		}
	}

	/**
	 * Returns whether the application id is running in this server
	 * 
	 * @param applicationId the id of the context
	 * @return whether it is running or not
	 */
	public boolean isRunning(String applicationId) {
		CompositeTemplate definition = this.definitions.get(applicationId);
		return definition != null;
	}
	
	public ProfilingInfo getProfilingInfo(String applicationId) {
		ProfilingInfo info = new ProfilingInfo();
		
		List<PerformanceProcessor> profilers = this.getProfilers(applicationId);
		for (PerformanceProcessor profiler : profilers) {
			info.addAverage(this.wrapperToObjectIdMapping.get(profiler), profiler.getAverage());
		}
		return info;
	}

	private List<PerformanceProcessor> getProfilers(String applicationId) {
		if (!this.profilers.containsKey(applicationId)) {
			synchronized (this.profilers) {
				if (!this.profilers.containsKey(applicationId)) {
					this.profilers.put(applicationId, new ArrayList<PerformanceProcessor>());
				}
			}
		}
		
		return this.profilers.get(applicationId);
	}

	public void addProfiler(String applicationId, String objectId) {
		CompositeTemplate CompositeTemplate = this.definitions.get(applicationId);
		ElementTemplate objectDefinition = CompositeTemplate.getDefinition(objectId);
		Object object = objectDefinition.getInstance();
		if (!(object instanceof ProcessorWrapper) && (object instanceof Processor)  
				|| (object instanceof ProcessorWrapper && !((ProcessorWrapper) object).isWrapWith(PerformanceProcessor.class))) {
			Processor processor = (Processor) object;
			PerformanceProcessor wrapper = new PerformanceProcessor(processor);
			objectDefinition.overrideObject(wrapper);
			this.getProfilers(applicationId).add(wrapper);
			this.wrapperToObjectIdMapping.put(wrapper, objectId);
		}
	}

	public void removeProfiler(String applicationId, String objectId) {
		CompositeTemplate CompositeTemplate = this.definitions.get(applicationId);
		ElementTemplate objectDefinition = CompositeTemplate.getDefinition(objectId);
		Object object = objectDefinition.getInstance();
		if (object instanceof ProcessorWrapper && ((ProcessorWrapper) object).isWrapWith(PerformanceProcessor.class)) {
			ProcessorWrapper wrapper = (ProcessorWrapper) object;
			Processor processor = wrapper.unwrap(PerformanceProcessor.class);
			objectDefinition.overrideObject(processor);
			this.getProfilers(applicationId).remove(wrapper);
			this.wrapperToObjectIdMapping.remove(wrapper);
		}
	}

	
	public void addLogger(
			String applicationId, 
			String objectId, 
			String beforeExpression, 
			String afterExpression, 
			boolean isBeforeConditional, 
			boolean isAfterConditional, 
			String beforeConditionalExpressionValue, 
			String afterConditionalExpressionValue) {
		
		CompositeTemplate compositeTemplate = this.definitions.get(applicationId);
		ElementTemplate objectDefinition = compositeTemplate.getDefinition(objectId);
		Object object = objectDefinition.getInstance();
		if (!(object instanceof ProcessorWrapper) && (object instanceof Processor) 
				|| (object instanceof ProcessorWrapper && !((ProcessorWrapper) object).isWrapWith(LogProcessorWrapper.class))) {
			Processor processor = (Processor) object;
			LogProcessorWrapper wrapper = new LogProcessorWrapper(processor);
			wrapper.setBeforeExpression(beforeExpression);
			wrapper.setAfterExpression(afterExpression);
			
			if (isBeforeConditional) {
				wrapper.setBeforeConditionExpression(beforeConditionalExpressionValue);
			}
			if (isAfterConditional) {
				wrapper.setAfterConditionExpression(afterConditionalExpressionValue);
			}
			
			objectDefinition.overrideObject(wrapper);
			this.getLoggers(applicationId).add(wrapper);
			this.wrapperToObjectIdMapping.put(wrapper, objectId);
			this.objectIdLogs.put(objectId, wrapper);
		}
	}
	
	public void removeLogger(String applicationId, String objectId) {
		CompositeTemplate CompositeTemplate = this.definitions.get(applicationId);
		ElementTemplate objectDefinition = CompositeTemplate.getDefinition(objectId);
		Object object = objectDefinition.getInstance();
		if (object instanceof ProcessorWrapper && ((ProcessorWrapper) object).isWrapWith(LogProcessorWrapper.class)) {
			ProcessorWrapper wrapper = (ProcessorWrapper) object;
			Processor processor = wrapper.unwrap(LogProcessorWrapper.class);
			objectDefinition.overrideObject(processor);
			this.getLoggers(applicationId).remove(wrapper);
			this.wrapperToObjectIdMapping.remove(wrapper);
			this.objectIdLogs.remove(objectId);
		}
	}
	
	private List<LogProcessorWrapper> getLoggers(String applicationId) {
		if (!this.logs.containsKey(applicationId)) {
			synchronized (this.logs) {
				if (!this.logs.containsKey(applicationId)) {
					this.logs.put(applicationId, new ArrayList<LogProcessorWrapper>());
				}
			}
		}
		
		return this.logs.get(applicationId);
	}

	public LogInfo getLogInfo(String applicationId) {
		List<LogProcessorWrapper> logs = this.getLoggers(applicationId);
		LogInfo info = new LogInfo();
		for (LogProcessorWrapper log : logs) {
			List<String> lines = log.lines();
			info.addLogLines(this.wrapperToObjectIdMapping.get(log), lines);
		}
		return info;
	}

	public List<String> getLogLines(String applicationId, String objectId) {
		LogProcessorWrapper wrapper = this.objectIdLogs.get(objectId);
		if (wrapper == null) {
			return new ArrayList<String>();
		}
		List<String> lines = wrapper.lines();
		return lines == null ? new ArrayList<String>() : lines;
	}

	public void addLazyComputedCache(
			String applicationId, 
			String objectId,
			String memcachedURL, 
			String keyExpression, 
			String cacheExpressions,
			int ttl) {

		CompositeTemplate context = this.definitions.get(applicationId);
		LazyComputedCacheTransformation transformation = new LazyComputedCacheTransformation(
				objectId, memcachedURL, keyExpression, cacheExpressions, ttl, this.mapping);
		
		transformation.transform(context);
	}
	
	public void addLazyAutorefreshableCache(
			String applicationId, 
			String objectId,
			String memcachedURL, 
			String keyExpression, 
			String cacheExpressions,
			int oldCacheEntryInMills) {

		CompositeTemplate context = this.definitions.get(applicationId);
		LazyAutorefreshableCacheTransformation transformation = new LazyAutorefreshableCacheTransformation(
				objectId, memcachedURL, keyExpression, cacheExpressions, oldCacheEntryInMills, this.mapping);
		
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
