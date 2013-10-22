package com.service.core;

import junit.framework.TestCase;

import com.abstractions.api.Context;
import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.instance.common.AddPropertyProcessor;
import com.abstractions.instance.common.ToStringProcessor;
import com.abstractions.meta.example.Meta;
import com.abstractions.model.Library;
import com.abstractions.service.core.ContextDefinition;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;

public class ServiceTestCase extends TestCase {

	private NamesMapping mapping;
	private Library common;

	public void setUp() {
		this.mapping = new NamesMapping();
		
		this.common = Meta.getCommonLibrary();
		this.common.addBasicDefinitionForClass("ADD_PROPERTY", AddPropertyProcessor.class);
		this.common.addBasicDefinitionForClass("TO_STRING", ToStringProcessor.class);
		this.common.createMappings(mapping);
	}

	public void testDefinitions() throws ServiceException {
		ContextDefinition contextDefinition = new ContextDefinition(this.mapping);
		ObjectClazz addPropertyDefinition = new ObjectClazz(this.common.getDefinition("ADD_PROPERTY"));
		ObjectClazz toStringDefinition = new ObjectClazz(this.common.getDefinition("TO_STRING"));
		
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
