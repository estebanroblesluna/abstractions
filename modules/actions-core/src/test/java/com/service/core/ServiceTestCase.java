package com.service.core;

import junit.framework.TestCase;

import com.core.api.Context;
import com.core.common.AddPropertyProcessor;
import com.core.common.ToStringProcessor;

public class ServiceTestCase extends TestCase {

	private NamesMapping mapping;

	public void setUp() {
		this.mapping = new NamesMapping();
		this.mapping.addMapping("ADD_PROPERTY", AddPropertyProcessor.class);
		this.mapping.addMapping("TO_STRING", ToStringProcessor.class);
	}

	public void testDefinitions() throws ServiceException {
		ContextDefinition contextDefinition = new ContextDefinition(this.mapping);
		ObjectDefinition addPropertyDefinition = new ObjectDefinition("ADD_PROPERTY");
		ObjectDefinition toStringDefinition = new ObjectDefinition("TO_STRING");
		
		contextDefinition.addDefinition(addPropertyDefinition);
		contextDefinition.addDefinition(toStringDefinition);
		
		addPropertyDefinition.setProperty("key", "http.a");
		addPropertyDefinition.setProperty("processor", "urn:" + toStringDefinition.getId());
		
		Context context = contextDefinition.instantiate();
		contextDefinition.initialize();
		
		
		AddPropertyProcessor addPropertyProcessor = context.getObjectWithId(addPropertyDefinition.getId());
		ToStringProcessor toStringProcessor = context.getObjectWithId(toStringDefinition.getId());
		
		assertNotNull(addPropertyProcessor);
		assertNotNull(toStringProcessor);
		
		assertEquals("http.a", addPropertyProcessor.getKey());
		assertEquals(toStringProcessor, addPropertyProcessor.getProcessor());
	}

}
