package com.abstractions.clazz.generalization;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jsoup.helper.Validate;

import com.abstractions.clazz.core.ObjectClazz;

public class AbstractionClazz {

	private ObjectClazz startingDefinition;
	private Map<String, ObjectClazz> definitions;
	
	public AbstractionClazz(ObjectClazz startingDefinition) {
		Validate.notNull(startingDefinition);
		
		this.startingDefinition = startingDefinition;
		this.definitions = new ConcurrentHashMap<String, ObjectClazz>();
		
		this.addDefinition(startingDefinition);
	}

	public void addAllDefinitions(Collection<ObjectClazz> definitions) {
		for (ObjectClazz definition : definitions) {
			this.addDefinition(definition);
		}
	}

	public void addDefinition(ObjectClazz definition) {
		Validate.notNull(definition);
		
		this.definitions.put(definition.getId(), definition);
	}

	public ObjectClazz getStartingDefinition() {
		return startingDefinition;
	}

	public Map<String, ObjectClazz> getDefinitions() {
		return Collections.unmodifiableMap(this.definitions);
	}
}
