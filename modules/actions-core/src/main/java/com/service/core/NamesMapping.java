package com.service.core;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.expression.ScriptingLanguage;
import com.core.interpreter.Evaluator;
import com.core.meta.ElementDefinition;

public class NamesMapping {
	
	private static final Log log = LogFactory.getLog(NamesMapping.class);

	private Map<String, ElementDefinition> elementMapping;
	private Map<String, Map<String, String>> elementInitialProperties;
	private Map<String, ScriptingLanguage> languageMapping;
	private Map<String, Evaluator> evaluators;

	public NamesMapping() {
		this.elementMapping = new ConcurrentHashMap<String, ElementDefinition>();
		this.elementInitialProperties = new ConcurrentHashMap<String, Map<String, String>>();
		this.languageMapping = new ConcurrentHashMap<String, ScriptingLanguage>();
		this.evaluators = new ConcurrentHashMap<String, Evaluator>();
	}

	public void addLanguage(String name, ScriptingLanguage language) {
		this.languageMapping.put(name, language);
	}

	public void addMapping(String elementName, ElementDefinition definition) {
		this.elementMapping.put(elementName, definition);
	}

	public void addMapping(String elementName, ElementDefinition definition, Map<String, String> initialProperties) {
		this.elementMapping.put(elementName, definition);
		this.elementInitialProperties.put(elementName, initialProperties);
	}

	public void addMapping(String elementName, ScriptingLanguage language) {
		this.languageMapping.put(elementName, language);
	}

	public Class<?> getClassForElement(String elementName) {
		ElementDefinition definition = this.elementMapping.get(elementName);
		
		if (definition == null || definition.isScript()) {
			return null;
		} else {
			Class<?> theClass = null;
			try {
				theClass = Class.forName(definition.getImplementation());
			} catch (ClassNotFoundException e) {
				log.warn("Class not found", e);
			}
			return theClass;
		}
	}

	public ScriptingLanguage getLanguage(String language) {
		return this.languageMapping.get(language);
	}

	public Map<String, ScriptingLanguage> getLanguageMapping() {
		return Collections.unmodifiableMap(languageMapping);
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

	public void setEvaluators(Map<String, Evaluator> evaluators) {
		if (evaluators != null) {
			this.evaluators.putAll(evaluators);
		}
	}

	public Map<String, Evaluator> getEvaluators() {
		return evaluators;
	}

	public Map<String, String> getElementInitialProperties(String elementName) {
		return this.elementInitialProperties.get(elementName);
	}

	public void addEvaluator(String elementName, Evaluator evaluator) {
		this.evaluators.put(elementName, evaluator);
	}

	public ElementDefinition getDefinition(String name) {
		return this.elementMapping.get(name);
	}
}
