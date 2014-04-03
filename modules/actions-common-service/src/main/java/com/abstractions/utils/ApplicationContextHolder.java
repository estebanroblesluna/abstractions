package com.abstractions.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHolder implements ApplicationContextAware {

	private static ApplicationContextHolder instance;
	private ApplicationContext applicationContext;
	
	public ApplicationContextHolder() {
	}
	
	public ApplicationContextHolder(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public static ApplicationContextHolder getInstance() {
		return instance;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		instance = new ApplicationContextHolder(context);
	}
	
	public Object getBean(String beanName) {
		return this.applicationContext.getBean(beanName);
	}

}
