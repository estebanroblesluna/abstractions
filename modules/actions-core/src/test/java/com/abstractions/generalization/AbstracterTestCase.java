package com.abstractions.generalization;

import junit.framework.TestCase;

import com.abstractions.impl.generalization.Abstracter;
import com.abstractions.impl.generalization.MultipleEntryPointsException;
import com.abstractions.impl.generalization.UnconnectedDefinitionsException;
import com.abstractions.model.Library;
import com.common.expression.ScriptingLanguage;
import com.core.impl.ConnectionType;
import com.core.meta.Meta;
import com.service.core.ContextDefinition;
import com.service.core.NamesMapping;
import com.service.core.ObjectDefinition;

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
		ObjectDefinition a = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		ObjectDefinition b = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		ObjectDefinition c = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		
		this.context.addDefinition(a);
		this.context.addDefinition(b);
		this.context.addDefinition(c);
		
		ObjectDefinition ab = this.context.addConnection(a, b, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectDefinition bc = this.context.addConnection(b, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
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
		ObjectDefinition a = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		ObjectDefinition b = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		ObjectDefinition c = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		ObjectDefinition d = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		
		this.context.addDefinition(a);
		this.context.addDefinition(b);
		this.context.addDefinition(c);
		this.context.addDefinition(d);
		
		ObjectDefinition ac = this.context.addConnection(a, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectDefinition bd = this.context.addConnection(b, d, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectDefinition cd = this.context.addConnection(c, d, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
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
		ObjectDefinition a = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		ObjectDefinition b = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		ObjectDefinition c = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		ObjectDefinition d = new ObjectDefinition(this.common.getDefinition("GROOVY"));
		
		this.context.addDefinition(a);
		this.context.addDefinition(b);
		this.context.addDefinition(c);
		this.context.addDefinition(d);
		
		ObjectDefinition ac = this.context.addConnection(a, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectDefinition bc = this.context.addConnection(b, c, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectDefinition cd = this.context.addConnection(c, d, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
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
