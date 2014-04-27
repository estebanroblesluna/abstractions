package com.abstractions.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.model.Application;
import com.abstractions.model.ApplicationSnapshot;
import com.abstractions.model.Flow;
import com.abstractions.model.Property;
import com.abstractions.model.Resource;
import com.abstractions.repository.GenericRepository;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ResourceService;
import com.abstractions.service.repository.CompositeTemplateMarshaller;
import com.abstractions.service.repository.MarshallingException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

@Service
public class SnapshotService {

	private static final Log log = LogFactory.getLog(SnapshotService.class);
	private static final String SNAPSHOTS_DIRECTORY = "snapshots";
	
	private String rootPath;
	private File rootDir;
	private GenericRepository repository;
	private ResourceService publicResourceService;
	private ResourceService privateResourceService;
	private ApplicationService applicationService;
	private List<ResourceAppender> resourceAppenders;
	private List<SnapshotProcessor> snapshotProcessors;

	
	private void initializeDirectory() {
		try {
			this.setRootDir(new File(this.getRootPath()));
			FileUtils.forceMkdir(this.getRootDir());
		} catch (IOException e) {
			log.error("Error intializing root directory", e);
		}
	}
	
	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	private File getRootDir() {
		return rootDir;
	}

	private void setRootDir(File rootDir) {
		this.rootDir = rootDir;
	}
	
	protected SnapshotService() { }
	
	public SnapshotService(GenericRepository repository, ApplicationService applicationService, ResourceService publicResourceService, ResourceService privateResourceService, List<ResourceAppender> resourceAppenders, List<SnapshotProcessor> snapshotProcessors, String rootPath) {
		Validate.notNull(repository);
		Validate.notNull(applicationService);
		Validate.notNull(publicResourceService);
		Validate.notNull(privateResourceService);
		Validate.notNull(resourceAppenders);
		Validate.notNull(snapshotProcessors);
		Validate.notNull(rootPath);
		
		this.repository = repository;
		this.resourceAppenders = resourceAppenders;
		this.applicationService = applicationService;
		this.publicResourceService = publicResourceService;
		this.privateResourceService = privateResourceService;
		this.snapshotProcessors = snapshotProcessors;
		this.rootPath = rootPath;
		this.initializeDirectory();
	}
	
	@Transactional
	public void generateSnapshot(long applicationId) {
		Application application = this.applicationService.getApplication(applicationId);
		
		ApplicationSnapshot snapshot = new ApplicationSnapshot(application);
		
		//clone all properties
		for (Property property : application.getProperties()) {
			try {
				Property clonedProperty = property.clone();
				this.repository.save(clonedProperty);
				snapshot.addProperty(clonedProperty);
			} catch (CloneNotSupportedException e) {
				log.warn("Error clonning property", e);
			}
		}
		
		//clone all flows
		for (Flow flow : application.getFlows()) {
			try {
				Flow clonedFlow = flow.clone();
				this.repository.save(clonedFlow);
				snapshot.addFlow(clonedFlow);
			} catch (CloneNotSupportedException e) {
				log.warn("Error clonning flow", e);
			}
		}

		try {
			this.processSnapshot(application, snapshot);
		} catch (Exception e) {
			log.error("Error when processing snapshot", e);
		}

		//clone all resources
		Resource cloned;
		Resource original;
		for(String resourceName : publicResourceService.listResources(applicationId)){
			 original = publicResourceService.getResource(applicationId, resourceName);
			 cloned = original.makeSnapshot();
			 repository.save(cloned);
			 snapshot.addResource(cloned);
		}
		
		for(String resourceName : privateResourceService.listResources(applicationId)){
			 original = privateResourceService.getResource(applicationId, resourceName);
			 cloned = original.makeSnapshot();
			 repository.save(cloned);
			 snapshot.addResource(cloned);
		}
		
		application.addSnapshot(snapshot);
		this.repository.save(application);
		this.repository.save(snapshot);
		
		try {
			this.persistSnapshot(application, snapshot);
		} catch (Exception e) {
			log.error("Error when persisting application snapshot", e);
		}
		CloudFrontService cf = new CloudFrontService(this);
		cf.distributeResources(snapshot.getId());

	}

	private void persistSnapshot(Application application, ApplicationSnapshot snapshot) throws Exception {
		try {
			ZipOutputStream zipOutputStream = new ZipOutputStream(this.getSnapshotOutputStream(application.getId(),snapshot.getId()));
			for (ResourceAppender resourceAppender : this.resourceAppenders) {
				for (Resource resource : resourceAppender.getResources()) {
					zipOutputStream.putNextEntry(new ZipEntry("files/" + resource.getPath()));
					IOUtils.copy(new ByteArrayInputStream(resource.getData()), zipOutputStream);
				}
			}
			for (Flow flow : application.getFlows()) {
				zipOutputStream.putNextEntry(new ZipEntry("flows/" + flow.getName() + ".json"));
				IOUtils.write(flow.getJson(), zipOutputStream);
			}
			zipOutputStream.close();
		} catch (IOException e) {
			log.warn("Error persisting snapshot", e);
		}
	}
	
	private void processSnapshot(Application application, ApplicationSnapshot snapshot) throws Exception {
		for (SnapshotProcessor snapshotProcessor : this.snapshotProcessors) {
			snapshotProcessor.process(application, snapshot);
		}
	}

	/*
	private void processResources(Application application, ApplicationSnapshot snapshot) throws MarshallingException {
		for (Flow flow : snapshot.getFlows()) {
			ApplicationDefinition applicationDefinition = new ApplicationDefinition(application.getName());
			new CompositeTemplateMarshaller(this.namesMapping).unmarshall(applicationDefinition, flow.getJson());
			Iterator<ElementTemplate> elementIterator = applicationDefinition.getDefinitions().values().iterator();
			while (elementIterator.hasNext()) {
				ElementTemplate element = elementIterator.next();
				if (element.getMeta().getImplementation().equals("RESOURCE_DUST_RENDERER")) {
					this.dustCompiler.mergeAndCompile(
						element.getProperty("bodyTemplatePath"),
						element.getProperty("resourcesList"),
						element.getProperty("templateRenderingList"));
				}
			}				
		}
	}
	*/
	
	@Transactional
	public List<ApplicationSnapshot> getSnapshots(long applicationId) {
		Application application = this.applicationService.getApplication(applicationId);
		
		List<ApplicationSnapshot> snapshots = new ArrayList<ApplicationSnapshot>();
		snapshots.addAll(application.getSnapshots());
		return snapshots;
	}

	public ApplicationSnapshot getSnapshot(long snapshotId) {
		return this.repository.get(ApplicationSnapshot.class, snapshotId);
	}
	
	public InputStream getContentsOfSnapshot(long applicationId, long snapshotId) {
		try {
			return new FileInputStream(this.buildSnapshotPath(applicationId, snapshotId));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public OutputStream getSnapshotOutputStream(long applicationId, long snapshotId) {
		try {
			File snapshotsDirectory = new File(this.buildSnapshotPath(applicationId, null));
			if (!snapshotsDirectory.exists()) {
				snapshotsDirectory.mkdirs();
			}
			return new FileOutputStream(this.buildSnapshotPath(applicationId, snapshotId));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String buildSnapshotPath(Long applicationId, Long snapshotId) {
		if (snapshotId == null) {
			return this.getRootPath() + File.separator + applicationId + File.separator + SNAPSHOTS_DIRECTORY;
		} else {
			return this.getRootPath() + File.separator + applicationId + File.separator + SNAPSHOTS_DIRECTORY + File.separator + "___snapshot_" + snapshotId;
		}
	}

	public void storeSnapshot(String applicationId, String snapshotId, InputStream content) {
		try {
			IOUtils.copy(content, new FileOutputStream(this.buildSnapshotPath(Long.parseLong(applicationId), Long.parseLong(snapshotId))));
		} catch (Exception e) {
			log.error("Error storing snapshot", e);
		}
	}
}
