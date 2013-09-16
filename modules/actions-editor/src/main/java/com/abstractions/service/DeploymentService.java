package com.abstractions.service;

import java.util.Collection;
import java.util.List;

import org.jsoup.helper.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abstractions.model.ApplicationSnapshot;
import com.abstractions.model.Deployment;
import com.abstractions.model.User;
import com.abstractions.repository.GenericRepository;

@Service
public class DeploymentService {

	private GenericRepository repository;
	private SnapshotService snapshotService;
	private UserService userService;
	private ServerService serverService;

	public DeploymentService() {
	}

	public DeploymentService(GenericRepository repository, SnapshotService snapshotService, UserService userService, ServerService serverService) {
		Validate.notNull(repository);
		Validate.notNull(snapshotService);
		Validate.notNull(userService);
		Validate.notNull(serverService);
		
		this.repository = repository;
		this.snapshotService = snapshotService;
		this.userService = userService;
		this.serverService = serverService;
	}

	@Transactional
	public void addDeployment(long snapshotId, long userId, Collection<Long> servers) {
		ApplicationSnapshot applicationSnapshot = this.snapshotService.getSnapshot(snapshotId);
		User user = this.userService.getUser(userId);
		Deployment deployment = new Deployment(applicationSnapshot, user);

		for (long serverId : servers) {
			deployment.addServer(this.serverService.getServer(serverId));
		}

		this.repository.save(deployment);
	}

	@Transactional
	public List<Deployment> getDeployments(long applicationSnapshotId) {
		return this.repository.get(Deployment.class, "snapshot", applicationSnapshotId);
	}

}
