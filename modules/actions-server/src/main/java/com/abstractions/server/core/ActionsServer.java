package com.abstractions.server.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

import com.abstractions.instance.common.LogInterceptor;
import com.abstractions.instance.common.PerformanceInterceptor;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.model.LoggingInfo;
import com.abstractions.model.ProfilingInfo;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ResourceService;
import com.abstractions.service.core.ServiceException;
import com.abstractions.service.repository.CompositeTemplateMarshaller;
import com.abstractions.service.repository.MarshallingException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;
import com.google.common.collect.Sets;

public class ActionsServer {

	private static Log log = LogFactory.getLog(ActionsServer.class);

	private final CompositeTemplateMarshaller marshaller;
	private final Map<String, CompositeTemplate> definitions;
	
	private final Map<String, List<PerformanceInterceptor>> profilers;
	private final Map<String, List<LogInterceptor>> logs;
	private final Map<String, LogInterceptor> objectIdLogs;
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
		
		if (System.getenv("server_apps_directory") != null) {
			log.info("Found server_apps_directory set to: " + System.getenv("server_apps_directory"));
			this.applicationDirectory = System.getenv("server_apps_directory");
		} else {
			this.applicationDirectory = applicationDirectory;
		}

		this.resourceService = resourceService;
		
		this.marshaller = new CompositeTemplateMarshaller(mapping);
		this.definitions = new ConcurrentHashMap<String, CompositeTemplate>();
		this.profilers = new ConcurrentHashMap<String, List<PerformanceInterceptor>>();
		this.objectIdLogs = new ConcurrentHashMap<String, LogInterceptor>();
		this.logs = new ConcurrentHashMap<String, List<LogInterceptor>>();
		this.statistics = new ConcurrentHashMap<String, StatisticsInterpreterDelegate>();
		this.wrapperToObjectIdMapping = new ConcurrentHashMap<Object, String>();
		
		this.startApplications();
 	}

	private void startApplications() {
		File applicationDirectory = new File(this.applicationDirectory + "/");
		File[] files = applicationDirectory.listFiles();
		if (files != null) {
			for (Object fileAsObject : files) {
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
	
	public LoggingInfo getLoggingInfo(String applicationId) {
		LoggingInfo info = new LoggingInfo(applicationId);
		
		List<LogInterceptor> logs = this.getLoggers(applicationId);
		for (LogInterceptor log : logs) {
			info.addLog(log.getElementId(), log.getLogAndReset());
		}
		
		return info;
	}
	
	public ProfilingInfo getProfilingInfo(String applicationId) {
		ProfilingInfo info = new ProfilingInfo(applicationId);
		
		List<PerformanceInterceptor> profilers = this.getProfilers(applicationId);
		for (PerformanceInterceptor profiler : profilers) {
			info.addAverage(profiler.getElementId(), profiler.getAverageAndReset());
		}
		
		return info;
	}

	private List<PerformanceInterceptor> getProfilers(String applicationId) {
		if (!this.profilers.containsKey(applicationId)) {
			synchronized (this.profilers) {
				if (!this.profilers.containsKey(applicationId)) {
					this.profilers.put(applicationId, new ArrayList<PerformanceInterceptor>());
				}
			}
		}
		
		return this.profilers.get(applicationId);
	}

	public void addProfiler(String applicationId, String objectId) {
		CompositeTemplate compositeTemplate = this.definitions.get(applicationId);
		ElementTemplate objectDefinition = compositeTemplate.getDefinition(objectId);
		PerformanceInterceptor existingInterceptor = objectDefinition.getFirstInterceptorOfClass(PerformanceInterceptor.class);
		
		if (existingInterceptor == null) {
			PerformanceInterceptor interceptor = new PerformanceInterceptor();
			interceptor.setElementId(objectId);
			this.getProfilers(applicationId).add(interceptor);
			this.wrapperToObjectIdMapping.put(interceptor, objectId);
			objectDefinition.addInterceptor(interceptor);
		}
	}

	public void removeProfiler(String applicationId, String objectId) {
		CompositeTemplate CompositeTemplate = this.definitions.get(applicationId);
		ElementTemplate objectDefinition = CompositeTemplate.getDefinition(objectId);
		PerformanceInterceptor existingInterceptor = objectDefinition.getFirstInterceptorOfClass(PerformanceInterceptor.class);

		if (existingInterceptor != null) {
			this.getProfilers(applicationId).remove(existingInterceptor);
			this.wrapperToObjectIdMapping.remove(existingInterceptor);
			objectDefinition.removeInterceptor(existingInterceptor);
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
		LogInterceptor existingInterceptor = objectDefinition.getFirstInterceptorOfClass(LogInterceptor.class);
		
		if (existingInterceptor == null) {
			LogInterceptor interceptor = new LogInterceptor();
			interceptor.setElementId(objectId);
			
			interceptor.setBeforeExpression(beforeExpression);
			interceptor.setAfterExpression(afterExpression);
			
			if (isBeforeConditional) {
				interceptor.setBeforeConditionExpression(beforeConditionalExpressionValue);
			}
			if (isAfterConditional) {
				interceptor.setAfterConditionExpression(afterConditionalExpressionValue);
			}

			this.getLoggers(applicationId).add(interceptor);
			this.wrapperToObjectIdMapping.put(interceptor, objectId);
			this.objectIdLogs.put(objectId, interceptor);
			objectDefinition.addInterceptor(interceptor);
		}
	}
	
	public void removeLogger(String applicationId, String objectId) {
		CompositeTemplate CompositeTemplate = this.definitions.get(applicationId);
		ElementTemplate objectDefinition = CompositeTemplate.getDefinition(objectId);
		LogInterceptor existingInterceptor = objectDefinition.getFirstInterceptorOfClass(LogInterceptor.class);

		if (existingInterceptor != null) {
			this.getLoggers(applicationId).remove(existingInterceptor);
			this.wrapperToObjectIdMapping.remove(existingInterceptor);
			this.objectIdLogs.remove(objectId);
			objectDefinition.removeInterceptor(existingInterceptor);
		}
	}
	
	private List<LogInterceptor> getLoggers(String applicationId) {
		if (!this.logs.containsKey(applicationId)) {
			synchronized (this.logs) {
				if (!this.logs.containsKey(applicationId)) {
					this.logs.put(applicationId, new ArrayList<LogInterceptor>());
				}
			}
		}
		
		return this.logs.get(applicationId);
	}

	public LogInfo getLogInfo(String applicationId) {
		List<LogInterceptor> logs = this.getLoggers(applicationId);
		LogInfo info = new LogInfo();
		for (LogInterceptor log : logs) {
			List<String> lines = log.lines();
			info.addLogLines(this.wrapperToObjectIdMapping.get(log), lines);
		}
		return info;
	}

	public List<String> getLogLines(String applicationId, String objectId) {
		LogInterceptor wrapper = this.objectIdLogs.get(objectId);
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
	
	public Collection<String> getApplicationIds() {
		Set<String> applicationIds = new HashSet<String>();
		applicationIds.addAll(this.profilers.keySet());
		applicationIds.addAll(this.logs.keySet());
		return applicationIds;
	}
}
