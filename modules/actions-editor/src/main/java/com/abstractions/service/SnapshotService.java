package com.abstractions.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Application;
import com.abstractions.model.ApplicationSnapshot;
import com.abstractions.model.Environment;
import com.abstractions.model.Flow;
import com.abstractions.model.Property;
import com.abstractions.model.Resource;
import com.abstractions.repository.GenericRepository;
import com.abstractions.service.core.ResourceService;

@Service
public class SnapshotService {

	private static final Log log = LogFactory.getLog(SnapshotService.class);
	
	private GenericRepository repository;
	private ResourceService publicResourceService;
	private ResourceService privateResourceService;
	private ApplicationService applicationService;
	private List<ResourceAppender> resourceAppenders;
	private List<SnapshotProcessor> snapshotProcessors;
	
	protected SnapshotService() { }
	
	public SnapshotService(GenericRepository repository, ApplicationService applicationService, ResourceService publicResourceService, ResourceService privateResourceService, List<ResourceAppender> resourceAppenders, List<SnapshotProcessor> snapshotProcessors, String rootPath) {
		Validate.notNull(repository);
		Validate.notNull(applicationService);
		Validate.notNull(publicResourceService);
		Validate.notNull(privateResourceService);
		Validate.notNull(resourceAppenders);
		Validate.notNull(snapshotProcessors);
		
		this.repository = repository;
		this.resourceAppenders = resourceAppenders;
		this.applicationService = applicationService;
		this.publicResourceService = publicResourceService;
		this.privateResourceService = privateResourceService;
		this.snapshotProcessors = snapshotProcessors;
	}
	
	@Transactional
	public void generateSnapshot(long applicationId, Environment env) {
		Application application = this.applicationService.getApplication(applicationId);
		
		ApplicationSnapshot snapshot = new ApplicationSnapshot(application);
		snapshot.setEnvironment(env);
		
		//clone all properties
		for (Property property : application.getProperties(env)) {
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
		
		Resource okResource = new Resource(applicationId, "_ok", "OK".getBytes(), "P");
		okResource.setSnapshot(true);
		
    snapshot.addResource(okResource);
		
		application.addSnapshot(snapshot);

    this.repository.save(application);
    this.repository.save(snapshot);
	}
	
	private void processSnapshot(Application application, ApplicationSnapshot snapshot) throws Exception {
		for (SnapshotProcessor snapshotProcessor : this.snapshotProcessors) {
			snapshotProcessor.process(application, snapshot);
		}
	}
	
	@Transactional
	public List<ApplicationSnapshot> getSnapshots(long applicationId) {
		Application application = this.applicationService.getApplication(applicationId);
		
		List<ApplicationSnapshot> snapshots = new ArrayList<ApplicationSnapshot>();
		snapshots.addAll(application.getSnapshots());
		return snapshots;
	}

	@Transactional
	public ApplicationSnapshot getSnapshot(long snapshotId) {
		return this.repository.get(ApplicationSnapshot.class, snapshotId);
	}

	@Transactional
  public InputStream getZipFor(long snapshotId) throws IOException {
	  ApplicationSnapshot snapshot = this.getSnapshot(snapshotId);
    Application application = snapshot.getApplication();
    ByteArrayOutputStream bytes = new ByteArrayOutputStream(4096);
    ZipOutputStream zipOutputStream = new ZipOutputStream(bytes);
      for (ResourceAppender resourceAppender : this.resourceAppenders) {
        for (Resource resource : resourceAppender.getResources()) {
          zipOutputStream.putNextEntry(new ZipEntry(resource.getPath()));
          IOUtils.copy(new ByteArrayInputStream(resource.getData()), zipOutputStream);
        }
      }
      for (Flow flow : application.getFlows()) {
        zipOutputStream.putNextEntry(new ZipEntry("flows/" + flow.getName() + ".json"));
        IOUtils.write(flow.getJson(), zipOutputStream);
      }
      for (String resourceName : this.privateResourceService.listResources(application.getId())) {
        zipOutputStream.putNextEntry(new ZipEntry("resources/private/" + resourceName));
        IOUtils.write(this.privateResourceService.getResource(application.getId(), resourceName).getData(), zipOutputStream);
      }
      for (String resourceName : this.publicResourceService.listResources(application.getId())) {
        zipOutputStream.putNextEntry(new ZipEntry("resources/public/" + resourceName));
        IOUtils.write(this.publicResourceService.getResource(application.getId(), resourceName).getData(), zipOutputStream);
      }
      //Properties
      zipOutputStream.putNextEntry(new ZipEntry("properties"));
      for(Property property : snapshot.getProperties()){
        IOUtils.write(property.getName() + "=" + property.getValue()+"\n", zipOutputStream);
      }
      zipOutputStream.closeEntry();
      zipOutputStream.close();
      return new ByteArrayInputStream(bytes.toByteArray());
  }
}
