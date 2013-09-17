package com.abstractions.service;

import java.util.ArrayList;
import java.util.List;

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

@Service
public class SnapshotService {

	private static final Log log = LogFactory.getLog(SnapshotService.class);
	
	private GenericRepository repository;
	private ApplicationService applicationService;
	
	protected SnapshotService() { }
	
	public SnapshotService(GenericRepository repository, ApplicationService applicationService) {
		Validate.notNull(repository);
		Validate.notNull(applicationService);
		
		this.repository = repository;
		this.applicationService = applicationService;
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
