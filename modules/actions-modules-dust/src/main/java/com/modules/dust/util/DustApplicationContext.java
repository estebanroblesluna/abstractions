package com.modules.dust.util;

import com.abstractions.service.core.ResourceService;
import com.abstractions.utils.ApplicationContextHolder;

public class DustApplicationContext {

	private static DustApplicationContext instance;
	
	public static DustApplicationContext getInstance() {
		if (instance == null) {
			instance = new DustApplicationContext();
		}
		return instance;
	}
	
	private DustApplicationContext() {
	}
	
	public ResourceService getPublicResourceService() {
		return (ResourceService) ApplicationContextHolder.getInstance().getBean("publicResourceService");
	}
	
	public ResourceService getPrivateResourceService() {
		return (ResourceService) ApplicationContextHolder.getInstance().getBean("privateResourceService");
	}
	
}
