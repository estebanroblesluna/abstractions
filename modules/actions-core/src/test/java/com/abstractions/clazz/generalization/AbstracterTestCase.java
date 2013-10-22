package com.abstractions.clazz.generalization;

import junit.framework.TestCase;

import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.expression.ScriptingLanguage;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.meta.example.Meta;
import com.abstractions.model.Library;
import com.service.core.ContextDefinition;
import com.service.core.NamesMapping;

public class AbstracterTestCase extends TestCase {

	private ContextDefinition context;
	private Library common;
	private Abstracter abstracter;

	public void setUp() {
		NamesMapping mapping = new NamesMapping();
		mapping.addLanguage("groovy", ScriptingLanguage.GROOVY);
		
		this.common = Meta.getCommonLibrary();
		this.common.createMappings(mapping);
		this.context = new ContextDefinition(mapping);
		this.abstracter = new Abstracter();
	}

	public void testUnconnectedDefinitionsException() {
		ObjectClazz a = new ObjectClazz(this.common.getDefinition("GROOVY"));
		ObjectClazz b = new ObjectClazz(this.common.getDefinition("GROOVY"));
		ObjectClazz c = new ObjectClazz(this.common.getDefinition("GROOVY"));
		
		this.context.addDefinition(a);
		this.context.addDefinition(b);
		this.context.addDefinition(c);
		
		ObjectClazz ab = this.context.addConnection(a, b, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectClazz bc = this.context.addConnection(b, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		try {
			this.abstracter.abstractFrom(context, a, b, ab);
			fail("Should have thrown UnconnectedDefinitionsException");
		} catch (UnconnectedDefinitionsException e) {
			//expected behavior
		} catch (MultipleEntryPointsException e) {
			fail("Should have thrown UnconnectedDefinitionsException");
		}
	}

	public void testMultipleEntryPointsException() {
		ObjectClazz a = new ObjectClazz(this.common.getDefinition("GROOVY"));
		ObjectClazz b = new ObjectClazz(this.common.getDefinition("GROOVY"));
		ObjectClazz c = new ObjectClazz(this.common.getDefinition("GROOVY"));
		ObjectClazz d = new ObjectClazz(this.common.getDefinition("GROOVY"));
		
		this.context.addDefinition(a);
		this.context.addDefinition(b);
		this.context.addDefinition(c);
		this.context.addDefinition(d);
		
		ObjectClazz ac = this.context.addConnection(a, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectClazz bd = this.context.addConnection(b, d, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectClazz cd = this.context.addConnection(c, d, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		try {
			this.abstracter.abstractFrom(context, c, d, cd);
			fail("Should have thrown MultipleEntryPointsException");
		} catch (UnconnectedDefinitionsException e) {
			fail("Should have thrown MultipleEntryPointsException");
		} catch (MultipleEntryPointsException e) {
			//expected behavior
		}	
	}
	
	public void testAbstraction() {
		ObjectClazz a = new ObjectClazz(this.common.getDefinition("GROOVY"));
		ObjectClazz b = new ObjectClazz(this.common.getDefinition("GROOVY"));
		ObjectClazz c = new ObjectClazz(this.common.getDefinition("GROOVY"));
		ObjectClazz d = new ObjectClazz(this.common.getDefinition("GROOVY"));
		
		this.context.addDefinition(a);
		this.context.addDefinition(b);
		this.context.addDefinition(c);
		this.context.addDefinition(d);
		
		ObjectClazz ac = this.context.addConnection(a, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectClazz bc = this.context.addConnection(b, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectClazz cd = this.context.addConnection(c, d, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		try {
			this.abstracter.abstractFrom(context, c, d, cd);
			//expected behavior
		} catch (UnconnectedDefinitionsException e) {
			fail("No exception expected");
		} catch (MultipleEntryPointsException e) {
			fail("No exception expected");
		}
	}
}
