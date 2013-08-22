package com.service.core;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.common.expression.ScriptingLanguage;
import com.core.interpreter.RouterEvaluator;

public class NamesMapping {

	private Map<String, Class<?>> elementMapping;
	private Map<String, Map<String, String>> elementInitialProperties;
	private Map<String, ScriptingLanguage> languageMapping;
	private Map<String, RouterEvaluator> routerEvaluators;
	
	public NamesMapping()
	{
		this.elementMapping = new ConcurrentHashMap<String, Class<?>>();
		this.elementInitialProperties = new ConcurrentHashMap<String, Map<String,String>>();
		this.languageMapping = new ConcurrentHashMap<String, ScriptingLanguage>();
		this.routerEvaluators = new ConcurrentHashMap<String, RouterEvaluator>();
	}

	public void addLanguage(String name, ScriptingLanguage language) {
		this.languageMapping.put(name, language);
	}
	
	public void addMapping(String elementName, String className) throws ClassNotFoundException {
		Class<?> theClass = Class.forName(className);
		this.addMapping(elementName, theClass);
	}

	public void addMapping(String elementName, Class<?> theClass) {
		this.elementMapping.put(elementName, theClass);
	}

	public void addMapping(String elementName, Class<?> theClass, Map<String, String> initialProperties) {
		this.elementMapping.put(elementName, theClass);
		this.elementInitialProperties.put(elementName, initialProperties);
	}

	public void addMapping(String elementName, ScriptingLanguage language) {
		this.languageMapping.put(elementName, language);
	}

	public Class<?> getClassForElement(String elementName) {
		return this.elementMapping.get(elementName);
	}
	
	public ScriptingLanguage getLanguage(String language) {
		return this.languageMapping.get(language);
	}

	public Map<String, Class<?>> getElementMapping() {
		return Collections.unmodifiableMap(elementMapping);
	}

	public Map<String, ScriptingLanguage> getLanguageMapping() {
		return Collections.unmodifiableMap(languageMapping);
	}

	public void setElementMapping(Map<String, Class<?>> elementMapping) {
		if (elementMapping != null) {
			this.elementMapping.putAll(elementMapping);
		}
	}

	public void setLanguageMapping(Map<String, ScriptingLanguage> languageMapping) {
		if (languageMapping != null) {
			this.languageMapping.putAll(languageMapping);
		}
	}
	
	public void setElementInitialProperties(Map<String, Map<String, String>> elementInitialProperties) {
		if (elementInitialProperties != null) {
			this.elementInitialProperties.putAll(elementInitialProperties);
		}
	}

	public void setRouterEvaluators(Map<String, RouterEvaluator> routerEvaluators) {
		if (routerEvaluators != null) {
			this.routerEvaluators.putAll(routerEvaluators);
		}
	}

	public Map<String, RouterEvaluator> getRouterEvaluators() {
		return routerEvaluators;
	}

	public Map<String, String> getElementInitialProperties(String elementName) {
		return this.elementInitialProperties.get(elementName);
	}

	public void addEvaluator(String elementName, RouterEvaluator evaluator) {
		this.routerEvaluators.put(elementName, evaluator);
	}
}
