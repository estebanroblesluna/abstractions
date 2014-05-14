package com.abstractions.runtime.interpreter;

import junit.framework.TestCase;

import com.abstractions.api.Message;
import com.abstractions.expression.ScriptingLanguage;
import com.abstractions.generalization.AbstractionEvaluator;
import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.instance.common.ListenerProcessor;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.meta.AbstractionDefinition;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.example.Meta;
import com.abstractions.model.Library;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class InterpreterTestCase extends TestCase {

	private ApplicationTemplate application;
	private Library common;
	private NamesMapping mapping;

	public void setUp() {
		mapping = new NamesMapping();
		mapping.addLanguage("groovy", ScriptingLanguage.GROOVY);
		
		this.common = Meta.getCommonLibrary();
		this.common.addBasicDefinitionForClass("INC",  "message.payload = message.payload + 1");
		this.common.addBasicDefinitionForClass("INC2", "message.payload = message.payload + 2");
		this.common.addBasicDefinitionForClass("INC3", "message.payload = message.payload + 3");
		this.common.addBasicDefinitionForClass("LISTENER", ListenerProcessor.class);

		this.common.createMappings(mapping);
		
		mapping.addEvaluator("FLOW", new AbstractionEvaluator());
		
		this.application = new ApplicationTemplate(new ApplicationDefinition("myApp"), mapping);
	}
	
	
	public void testNextInChain() throws InterruptedException, ServiceException {
		ElementTemplate source = new ElementTemplate(this.common.getDefinition("INC"));
		ElementTemplate target = new ElementTemplate(this.common.getDefinition("INC"));

		this.application.addDefinition(source);
		this.application.addDefinition(target);
		this.application.addConnection(source.getId(), target.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		this.application.sync(this.mapping);
		
		Interpreter interpreter = new Interpreter(this.application, source, this.application);

		Message message = new Message();
		message.setPayload(0l);
		
		Thread thread = interpreter.run(message);
		
		assertEquals(2l, thread.getCurrentMessage().getPayload());
	}
	
	public void testChoiceRouter() throws ServiceException {
		ElementTemplate source = new ElementTemplate(this.common.getDefinition("INC"));
		ElementTemplate router = new ElementTemplate(this.common.getDefinition("CHOICE"));
		ElementTemplate inc2 = new ElementTemplate(this.common.getDefinition("INC2"));
		ElementTemplate inc3 = new ElementTemplate(this.common.getDefinition("INC3"));

		this.application.addDefinition(source);
		this.application.addDefinition(router);
		this.application.addDefinition(inc2);
		this.application.addDefinition(inc3);

		this.application.addConnection(source.getId(), router.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		String inc2ConnectionId = this.application.addConnection(router.getId(), inc2.getId(), ConnectionType.CHOICE_CONNECTION).getId();
		String inc3ConnectionId = this.application.addConnection(router.getId(), inc3.getId(), ConnectionType.CHOICE_CONNECTION).getId();
		
		
		ElementTemplate inc2Connection = this.application.getDefinition(inc2ConnectionId);
		inc2Connection.setProperty("expression", "message.properties['val'] == 'aaa'");
		ElementTemplate inc3Connection = this.application.getDefinition(inc3ConnectionId);
		inc3Connection.setProperty("expression", "message.properties['val'] == 'bbb'");
		
		this.application.sync(this.mapping);
		
		Interpreter interpreter = new Interpreter(this.application, source, this.application);

		//case inc2 router
		Message message = new Message();
		message.setPayload(0l);
		message.putProperty("val", "aaa");
		
		Thread thread = interpreter.run(message);
		assertEquals(3l, thread.getCurrentMessage().getPayload());

		
		//case inc3 router
		message = new Message();
		message.setPayload(0l);
		message.putProperty("val", "bbb");
		
		thread = interpreter.run(message);
		assertEquals(4l, thread.getCurrentMessage().getPayload());

		
		//case default router
		message = new Message();
		message.setPayload(0l);
		message.putProperty("val", "ccc");
		
		thread = interpreter.run(message);
		assertEquals(1l, thread.getCurrentMessage().getPayload());
	}
	
	public void testAllRouter() throws ServiceException {
		ElementTemplate source = new ElementTemplate(this.common.getDefinition("INC"));
		ElementTemplate router = new ElementTemplate(this.common.getDefinition("ALL"));
		ElementTemplate inc2 = new ElementTemplate(this.common.getDefinition("INC2"));
		ElementTemplate inc3 = new ElementTemplate(this.common.getDefinition("INC3"));

		this.application.addDefinition(source);
		this.application.addDefinition(router);
		this.application.addDefinition(inc2);
		this.application.addDefinition(inc3);

		this.application.addConnection(source.getId(), router.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		String inc2ConnectionId = this.application.addConnection(router.getId(), inc2.getId(), ConnectionType.ALL_CONNECTION).getId();
		String inc3ConnectionId = this.application.addConnection(router.getId(), inc3.getId(), ConnectionType.ALL_CONNECTION).getId();
		
		
		ElementTemplate inc2Connection = this.application.getDefinition(inc2ConnectionId);
		inc2Connection.setProperty("targetExpression", "message.properties['inc2'] = result.payload");
		ElementTemplate inc3Connection = this.application.getDefinition(inc3ConnectionId);
		inc3Connection.setProperty("targetExpression", "message.properties['inc3'] = result.payload");
		
		this.application.sync(this.mapping);
		
		Interpreter interpreter = new Interpreter(this.application, source, this.application);

		Message message = new Message();
		message.setPayload(0l);
		
		Thread thread = interpreter.run(message);
		assertEquals(1l, thread.getCurrentMessage().getPayload());
		assertEquals(3l, thread.getCurrentMessage().getProperty("inc2"));
		assertEquals(4l, thread.getCurrentMessage().getProperty("inc3"));
	}

	public void testWireTapRouter() throws ServiceException, InterruptedException {
		ElementTemplate source = new ElementTemplate(this.common.getDefinition("INC"));
		ElementTemplate router = new ElementTemplate(this.common.getDefinition("WIRE_TAP"));
		ElementTemplate inc2 = new ElementTemplate(this.common.getDefinition("INC2"));
		ElementTemplate listener = new ElementTemplate(this.common.getDefinition("LISTENER"));

		this.application.addDefinition(source);
		this.application.addDefinition(router);
		this.application.addDefinition(inc2);
		this.application.addDefinition(listener);

		this.application.addConnection(source.getId(), router.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		this.application.addConnection(router.getId(), inc2.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		this.application.addConnection(router.getId(), listener.getId(), ConnectionType.WIRE_TAP_CONNECTION);

		this.application.sync(this.mapping);
		
		Interpreter interpreter = new Interpreter(this.application, source, this.application);

		Message message = new Message();
		message.setPayload(0l);
		
		assertNull(((ListenerProcessor) listener.getInstance()).getLastMessage());
		
		Thread thread = interpreter.run(message);
		assertEquals(3l, thread.getCurrentMessage().getPayload());

		java.lang.Thread.sleep(500);
		
		assertNotNull(((ListenerProcessor) listener.getInstance()).getLastMessage());

		assertEquals(1l, ((ListenerProcessor) listener.getInstance()).getLastMessage().getPayload());
	}
	
	public void testSimpleAbstraction() throws ServiceException {
		AbstractionDefinition incBy2 = new AbstractionDefinition("INC_BY_2");

		ElementTemplate source = new ElementTemplate(this.common.getDefinition("INC"));
		ElementTemplate target = new ElementTemplate(this.common.getDefinition("INC"));
		ElementTemplate sourceTarget = this.application.createConnection(source, target, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		incBy2.addDefinition(source);
		incBy2.addDefinition(target);
		incBy2.addDefinition(sourceTarget);
		incBy2.setStartingDefinition(source);

		ElementTemplate inc2 = new ElementTemplate(incBy2);
		this.application.addDefinition(inc2);
		
		this.application.sync(this.mapping);
		
		Interpreter interpreter = new Interpreter(this.application, inc2, this.application);

		Message message = new Message();
		message.setPayload(0l);

		Thread thread = interpreter.run(message);
		assertEquals(2l, thread.getCurrentMessage().getPayload());
	}
	
	public void testSimpleAbstractionWithConnection() throws ServiceException {
		AbstractionDefinition incBy2 = new AbstractionDefinition("INC_BY_2");

		ElementTemplate source = new ElementTemplate(this.common.getDefinition("INC"));
		ElementTemplate target = new ElementTemplate(this.common.getDefinition("INC"));
		ElementTemplate sourceTarget = this.application.createConnection(source, target, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		incBy2.addDefinition(source);
		incBy2.addDefinition(target);
		incBy2.addDefinition(sourceTarget);
		incBy2.setStartingDefinition(source);

		ElementTemplate inc2_1 = new ElementTemplate(incBy2);
		ElementTemplate inc2_2 = new ElementTemplate(incBy2);
		ElementTemplate inc2_1_2 = this.application.createConnection(inc2_1, inc2_2, ConnectionType.NEXT_IN_CHAIN_CONNECTION);

		this.application.addDefinition(inc2_1);
		this.application.addDefinition(inc2_2);
		this.application.addDefinition(inc2_1_2);
		
		this.application.sync(this.mapping);
		
		Interpreter interpreter = new Interpreter(this.application, inc2_1, this.application);

		Message message = new Message();
		message.setPayload(0l);

		Thread thread = interpreter.run(message);
		assertEquals(4l, thread.getCurrentMessage().getPayload());
	}
	
	public void testAbstractionOfAbstraction() throws ServiceException {
		AbstractionDefinition incBy2 = new AbstractionDefinition("INC_BY_2");

		ElementTemplate source = new ElementTemplate(this.common.getDefinition("INC"));
		ElementTemplate target = new ElementTemplate(this.common.getDefinition("INC"));
		ElementTemplate sourceTarget = this.application.createConnection(source, target, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		incBy2.addDefinition(source);
		incBy2.addDefinition(target);
		incBy2.addDefinition(sourceTarget);
		incBy2.setStartingDefinition(source);

		
		AbstractionDefinition incBy6 = new AbstractionDefinition("INC_BY_6");

		ElementTemplate by6_1 = new ElementTemplate(incBy2);
		ElementTemplate by6_2 = new ElementTemplate(incBy2);
		ElementTemplate by6_3 = new ElementTemplate(incBy2);
		ElementTemplate by6_1_by6_2 = this.application.createConnection(by6_1, by6_2, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ElementTemplate by6_2_by6_3 = this.application.createConnection(by6_2, by6_3, ConnectionType.NEXT_IN_CHAIN_CONNECTION);

		incBy6.addDefinition(by6_1);
		incBy6.addDefinition(by6_2);
		incBy6.addDefinition(by6_3);
		incBy6.addDefinition(by6_1_by6_2);
		incBy6.addDefinition(by6_2_by6_3);
		incBy6.setStartingDefinition(by6_1);

		
		ElementTemplate add6 = new ElementTemplate(incBy6);
		this.application.addDefinition(add6);
		
		ElementTemplate inc3 = new ElementTemplate(this.common.getDefinition("INC3"));
		this.application.addDefinition(inc3);

		ElementTemplate add62 = new ElementTemplate(incBy6);
		this.application.addDefinition(add62);

		this.application.addConnection(add6.getId(), inc3.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		this.application.addConnection(inc3.getId(), add62.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		this.application.sync(this.mapping);
		
		Interpreter interpreter = new Interpreter(this.application, add6, this.application);

		Message message = new Message();
		message.setPayload(0l);

		Thread thread = interpreter.run(message);
		assertEquals(15l, thread.getCurrentMessage().getPayload());
	}
}
