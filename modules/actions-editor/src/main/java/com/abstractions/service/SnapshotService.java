package com.abstractions.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
import com.abstractions.template.ElementTemplate;
import com.modules.dust.ResourceBasedDustTemplateCompiler;

@Service
public class SnapshotService {

	private static final Log log = LogFactory.getLog(SnapshotService.class);
	
	private GenericRepository repository;
	private ApplicationService applicationService;
	private ResourceService resourceService;
	private ResourceProcessor resourceProcessor;
	private ResourceBasedDustTemplateCompiler dustCompiler;
	
	@Autowired
	private NamesMapping namesMapping;
	
	protected SnapshotService() { }
	
	public SnapshotService(GenericRepository repository, ApplicationService applicationService, ResourceService fileService, ResourceProcessor fileProcessor) {
		Validate.notNull(repository);
		Validate.notNull(applicationService);
		Validate.notNull(fileService);
		Validate.notNull(fileProcessor);
		
		this.repository = repository;
		this.applicationService = applicationService;
		this.resourceService = fileService;
		this.resourceProcessor = fileProcessor;
	}
	
	@Transactional
	public void generateSnapshot(long applicationId) {
		Application application = this.applicationService.getApplication(applicationId);
		
		ApplicationSnapshot snapshot = new ApplicationSnapshot();
		
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
		this.repository.save(snapshot);
		this.repository.save(application);
		
		try {
			this.persistSnapshot(application, snapshot);
		} catch (MarshallingException e) {
			log.error("Error when marshalling application snapshot", e);
		}
	}

	private void persistSnapshot(Application application, ApplicationSnapshot snapshot) throws MarshallingException {
		try {
			this.processResources(application, snapshot);
			ZipOutputStream zipOutputStream = new ZipOutputStream(this.resourceService.getSnapshotOutputStream(new Long(application.getId()).toString(), new Long(snapshot.getId()).toString()));
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
			log.error("Error persisting snapshot", e);
		}
	}
	
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
}
