package com.abstractions.generalization;

import junit.framework.TestCase;

import com.abstractions.expression.ScriptingLanguage;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.example.Meta;
import com.abstractions.model.Library;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class AbstracterTestCase extends TestCase {

	private CompositeTemplate application;
	private Library common;
	private Abstracter abstracter;

	public void setUp() {
		NamesMapping mapping = new NamesMapping();
		mapping.addLanguage("groovy", ScriptingLanguage.GROOVY);
		
		this.common = Meta.getCommonLibrary();
		this.common.createMappings(mapping);
		this.application = new ApplicationTemplate(new ApplicationDefinition("myApp"), mapping);
		this.abstracter = new Abstracter();
	}

	public void testUnconnectedDefinitionsException() {
		ElementTemplate a = new ElementTemplate(this.common.getDefinition("GROOVY"));
		ElementTemplate b = new ElementTemplate(this.common.getDefinition("GROOVY"));
		ElementTemplate c = new ElementTemplate(this.common.getDefinition("GROOVY"));
		
		this.application.addDefinition(a);
		this.application.addDefinition(b);
		this.application.addDefinition(c);
		
		ElementTemplate ab = this.application.addConnection(a, b, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		this.application.addConnection(b, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		try {
			this.abstracter.abstractFrom("MyFirstAbstraction", application, a, b, ab);
			fail("Should have thrown UnconnectedDefinitionsException");
		} catch (UnconnectedDefinitionsException e) {
			//expected behavior
		} catch (MultipleEntryPointsException e) {
			fail("Should have thrown UnconnectedDefinitionsException");
		}
	}

	public void testMultipleEntryPointsException() {
		ElementTemplate a = new ElementTemplate(this.common.getDefinition("GROOVY"));
		ElementTemplate b = new ElementTemplate(this.common.getDefinition("GROOVY"));
		ElementTemplate c = new ElementTemplate(this.common.getDefinition("GROOVY"));
		ElementTemplate d = new ElementTemplate(this.common.getDefinition("GROOVY"));
		
		this.application.addDefinition(a);
		this.application.addDefinition(b);
		this.application.addDefinition(c);
		this.application.addDefinition(d);
		
		this.application.addConnection(a, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		this.application.addConnection(b, d, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ElementTemplate cd = this.application.addConnection(c, d, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		try {
			this.abstracter.abstractFrom("MyFirstAbstraction", application, c, d, cd);
			fail("Should have thrown MultipleEntryPointsException");
		} catch (UnconnectedDefinitionsException e) {
			fail("Should have thrown MultipleEntryPointsException");
		} catch (MultipleEntryPointsException e) {
			//expected behavior
		}	
	}
	
	public void testAbstraction() {
		ElementTemplate a = new ElementTemplate(this.common.getDefinition("GROOVY"));
		ElementTemplate b = new ElementTemplate(this.common.getDefinition("GROOVY"));
		ElementTemplate c = new ElementTemplate(this.common.getDefinition("GROOVY"));
		ElementTemplate d = new ElementTemplate(this.common.getDefinition("GROOVY"));
		
		this.application.addDefinition(a);
		this.application.addDefinition(b);
		this.application.addDefinition(c);
		this.application.addDefinition(d);
		
		this.application.addConnection(a, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		this.application.addConnection(b, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ElementTemplate cd = this.application.addConnection(c, d, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		try {
			this.abstracter.abstractFrom("MyFirstAbstraction", application, c, d, cd);
			//expected behavior
		} catch (UnconnectedDefinitionsException e) {
			fail("No exception expected");
		} catch (MultipleEntryPointsException e) {
			fail("No exception expected");
		}
	}
}
