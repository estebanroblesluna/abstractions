package com.abstractions.service.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.abstractions.api.Identificable;

public class GeneralHolder<T extends Identificable> {

	private Map<String, T> definitions;
	
	public GeneralHolder() {
		this.definitions = new ConcurrentHashMap<String, T>();
	}
	
	public T get(String id) {
		return this.definitions.get(id);
	}
	
	public void put(T definition) {
		this.definitions.put(definition.getId(), definition);
	}
}
