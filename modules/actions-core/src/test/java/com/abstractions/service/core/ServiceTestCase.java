package com.abstractions.service.core;

import junit.framework.TestCase;

import com.abstractions.api.CompositeElement;
import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.instance.common.AddPropertyProcessor;
import com.abstractions.instance.common.ToStringProcessor;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.example.Meta;
import com.abstractions.model.Library;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

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
	  ApplicationTemplate application = new ApplicationTemplate(new ApplicationDefinition("myApp"), this.mapping);
		ElementTemplate addPropertyDefinition = new ElementTemplate(this.common.getDefinition("ADD_PROPERTY"));
		ElementTemplate toStringDefinition = new ElementTemplate(this.common.getDefinition("TO_STRING"));
		
		application.addDefinition(addPropertyDefinition);
		application.addDefinition(toStringDefinition);
		
		addPropertyDefinition.setProperty("key", "http.a");
		addPropertyDefinition.setProperty("processor", "urn:" + toStringDefinition.getId());
		
		application.sync();
		CompositeElement composite = (CompositeElement) application.getInstance();
		
		
		AddPropertyProcessor addPropertyProcessor = composite.getObjectWithId(addPropertyDefinition.getId());
		ToStringProcessor toStringProcessor = composite.getObjectWithId(toStringDefinition.getId());
		
		assertNotNull(addPropertyProcessor);
		assertNotNull(toStringProcessor);
		
		assertEquals("http.a", addPropertyProcessor.getKey());
		assertEquals(toStringProcessor, addPropertyProcessor.getProcessor());
	}

}
