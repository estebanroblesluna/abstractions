package com.abstractions.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncDeployer {

	private final DeploymentService deploymentService;
	private final ExecutorService asyncDeploymentService;

	public AsyncDeployer(DeploymentService deploymentService) {
		this.asyncDeploymentService = Executors.newFixedThreadPool(5);
		this.deploymentService = deploymentService;
	}

	public void deploy(final long deploymentId) {
		this.asyncDeploymentService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				deploymentService.deploy(deploymentId);
			}
		});
	}
}