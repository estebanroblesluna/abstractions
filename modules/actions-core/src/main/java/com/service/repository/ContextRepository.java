package com.service.repository;

import com.service.core.ContextDefinition;

public interface ContextRepository {

	void save(ContextDefinition definition) throws MarshallingException;
	
	ContextDefinition load(String contextId) throws MarshallingException;

	String getJsonDefinition(String contextId);
}
