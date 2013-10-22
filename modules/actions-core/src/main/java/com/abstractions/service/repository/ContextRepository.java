package com.abstractions.service.repository;

import com.abstractions.service.core.ContextDefinition;

public interface ContextRepository {

	void save(ContextDefinition definition) throws MarshallingException;
	
	ContextDefinition load(String contextId) throws MarshallingException;

	String getJsonDefinition(String contextId);
}
