package com.abstractions.service;

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
import com.abstractions.repository.GenericRepository;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ResourceService;
import com.abstractions.service.repository.CompositeTemplateMarshaller;
import com.abstractions.service.repository.MarshallingException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;
import com.modules.dust.ResourceBasedDustTemplateCompiler;

@Service
public class SnapshotService {

	private static final Log log = LogFactory.getLog(SnapshotService.class);
	private static final String SNAPSHOTS_DIRECTORY = "snapshots";
	
	private String rootPath;
	private File rootDir;
	private GenericRepository repository;
	private ApplicationService applicationService;
	private ResourceService resourceService;
	private ResourceProcessor resourceProcessor;
	private ResourceBasedDustTemplateCompiler dustCompiler;
	
	@Autowired
	private NamesMapping namesMapping;

	
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
	
	public SnapshotService(GenericRepository repository, ApplicationService applicationService, ResourceService fileService, ResourceProcessor fileProcessor, ResourceBasedDustTemplateCompiler dustCompiler, String rootPath) {
		Validate.notNull(repository);
		Validate.notNull(applicationService);
		Validate.notNull(fileService);
		Validate.notNull(fileProcessor);
		Validate.notNull(rootPath);
		
		this.repository = repository;
		this.applicationService = applicationService;
		this.resourceService = fileService;
		this.resourceProcessor = fileProcessor;
		this.dustCompiler = dustCompiler;
		this.setRootPath(rootPath);
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
		
		application.addSnapshot(snapshot);
		this.repository.save(application);
		this.repository.save(snapshot);
		
		try {
			this.persistSnapshot(application, snapshot);
		} catch (MarshallingException e) {
			log.error("Error when marshalling application snapshot", e);
		}
		CloudFrontService cf = new CloudFrontService(this);
		cf.distributeResources(snapshot.getId());

	}

	private void persistSnapshot(Application application, ApplicationSnapshot snapshot) throws MarshallingException {
		try {
			this.processResources(application, snapshot);
			ZipOutputStream zipOutputStream = new ZipOutputStream(this.getSnapshotOutputStream(application.getId(),snapshot.getId()));
			for (String filename : this.resourceService.listResources(application.getId())) {
				InputStream inputStream = this.resourceService.getContentsOfResource(application.getId(), filename);
				List<ResourceChange> changes = this.resourceProcessor.process(filename, inputStream);
				for (ResourceChange change : changes) {
					if (change.getAction().equals(ResourceAction.CREATE_OR_UPDATE)) {
						zipOutputStream.putNextEntry(new ZipEntry("files/" + filename));
						IOUtils.copy(change.getInputStream(), zipOutputStream);
						change.getInputStream().close();
					}
				}
				if (inputStream == null) {
					continue;
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
	
	private void processResources(Application application, ApplicationSnapshot snapshot) throws MarshallingException, IOException {
		for (Flow flow : snapshot.getFlows()) {
			ApplicationDefinition applicationDefinition = new ApplicationDefinition(application.getName());
			CompositeTemplate template = new CompositeTemplateMarshaller(this.namesMapping).unmarshall(applicationDefinition, flow.getJson());
			Iterator<ElementTemplate> elementIterator = template.getDefinitions().values().iterator();
			while (elementIterator.hasNext()) {
				ElementTemplate element = elementIterator.next();
				if (element.getMeta().getName().equals("RESOURCE_DUST_RENDERER")) {
					this.dustCompiler.mergeAndCompile(
						application.getId(),
						"http://localhost:8080/service/fileStore/2/files/",
						element.getProperties().get("name"),
						element.getProperty("bodyTemplatePath"),
						element.getProperty("resourcesList"),
						element.getProperty("templateRenderingList"));
				}
			}				
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
