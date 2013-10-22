package com.abstractions.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.Application;
import com.abstractions.model.ApplicationSnapshot;
import com.abstractions.model.Flow;
import com.abstractions.model.Property;
import com.abstractions.repository.GenericRepository;
import com.abstractions.service.core.FileService;

@Service
public class SnapshotService {

	private static final Log log = LogFactory.getLog(SnapshotService.class);
	
	private GenericRepository repository;
	private ApplicationService applicationService;
	private FileService fileService;
	private FileProcessor fileProcessor;
	
	protected SnapshotService() { }
	
	public SnapshotService(GenericRepository repository, ApplicationService applicationService, FileService fileService, FileProcessor fileProcessor) {
		Validate.notNull(repository);
		Validate.notNull(applicationService);
		Validate.notNull(fileService);
		Validate.notNull(fileProcessor);
		
		this.repository = repository;
		this.applicationService = applicationService;
		this.fileService = fileService;
		this.fileProcessor = fileProcessor;
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
		
		this.persistSnapshot(application, snapshot);
	}

	private void persistSnapshot(Application application, ApplicationSnapshot snapshot) {
		try {
			ZipOutputStream zipOutputStream = new ZipOutputStream(this.fileService.getSnapshotOutputStream(new Long(application.getId()).toString(), new Long(snapshot.getId()).toString()));
			for (String filename : this.fileService.listFiles(new Long(application.getId()).toString())) {
				InputStream inputStream = this.fileService.getContentsOfFile(new Long(application.getId()).toString(), filename);
				inputStream = this.fileProcessor.process(filename, inputStream);
				if (inputStream == null) {
					continue;
				}
				zipOutputStream.putNextEntry(new ZipEntry("files/" + filename));
				IOUtils.copy(inputStream, zipOutputStream);
				inputStream.close();
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
