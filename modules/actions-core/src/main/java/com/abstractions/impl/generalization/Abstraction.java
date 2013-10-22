package com.abstractions.impl.generalization;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.helper.Validate;

import com.service.core.ObjectDefinition;

public class Abstraction {

	private ObjectDefinition startingDefinition;
	private Map<String, ObjectDefinition> definitions;
	
	public Abstraction(ObjectDefinition startingDefinition) {
		Validate.notNull(startingDefinition);
		
		this.startingDefinition = startingDefinition;
		this.definitions = new ConcurrentHashMap<String, ObjectDefinition>();
		
		this.addDefinition(startingDefinition);
	}

	public void addAllDefinitions(Collection<ObjectDefinition> definitions) {
		for (ObjectDefinition definition : definitions) {
			this.addDefinition(definition);
		}
	}

	public void addDefinition(ObjectDefinition definition) {
		Validate.notNull(definition);
		
		this.definitions.put(definition.getId(), definition);
	}

	public ObjectDefinition getStartingDefinition() {
		return startingDefinition;
	}

	public Map<String, ObjectDefinition> getDefinitions() {
		return Collections.unmodifiableMap(this.definitions);
	}
}
