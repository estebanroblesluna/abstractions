package com.abstractions.utils;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
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
	  try {
		return this.applicationContext.getBean(beanName);
	  } catch (NoSuchBeanDefinitionException e) {
	    return null;
	  }
	}
	
	public String getProperty(String propertyName) {
	  try {
      return ((Properties)this.applicationContext.getBean("applicationProperties")).getProperty(propertyName);
    } catch (Exception e) {
      return null;
    }
	}

}
