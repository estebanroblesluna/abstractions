package com.service.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.service.core.ContextDefinition;
import com.service.core.NamesMapping;

public class InMemoryContextRepository implements ContextRepository {

	private ContextDefinitionMarshaller marshaller;
	private Map<String, String> definitions;
	
	public InMemoryContextRepository(NamesMapping mapping) {
		this.marshaller = new ContextDefinitionMarshaller(mapping);
		this.definitions = new ConcurrentHashMap<String, String>();
	}
	
	@Override
	public void save(ContextDefinition definition) throws MarshallingException {
		String marshalledDefinition = this.marshaller.marshall(definition);
		this.definitions.put(definition.getId(), marshalledDefinition);
	}

	@Override
	public ContextDefinition load(String contextId) throws MarshallingException {
		String marshalledDefinition = this.definitions.get(contextId);
		if (marshalledDefinition == null) {
			return null;
		}
		
		return this.marshaller.unmarshall(marshalledDefinition);
	}

	@Override
	public String getJsonDefinition(String contextId) {
		String marshalledDefinition = this.definitions.get(contextId);
		return marshalledDefinition;
	}
}
