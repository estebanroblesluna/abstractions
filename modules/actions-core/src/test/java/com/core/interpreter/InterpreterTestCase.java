package com.core.interpreter;

import junit.framework.TestCase;

import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.instance.common.ListenerProcessor;
import com.abstractions.meta.FlowDefinition;
import com.abstractions.meta.example.Meta;
import com.abstractions.model.Library;
import com.common.expression.ScriptingLanguage;
import com.core.api.Message;
import com.core.composition.FlowEvaluator;
import com.core.impl.ConnectionType;
import com.service.core.ContextDefinition;
import com.service.core.NamesMapping;
import com.service.core.ServiceException;

public class InterpreterTestCase extends TestCase {

	private ContextDefinition context;
	private Library common;

	public void setUp() {
		NamesMapping mapping = new NamesMapping();
		mapping.addLanguage("groovy", ScriptingLanguage.GROOVY);
		
		this.common = Meta.getCommonLibrary();
		this.common.addBasicDefinitionForClass("INC",  "message.payload = message.payload + 1");
		this.common.addBasicDefinitionForClass("INC2", "message.payload = message.payload + 2");
		this.common.addBasicDefinitionForClass("INC3", "message.payload = message.payload + 3");
		this.common.addBasicDefinitionForClass("LISTENER", ListenerProcessor.class);

		this.common.createMappings(mapping);
		
		mapping.addEvaluator("FLOW", new FlowEvaluator());
		
		this.context = new ContextDefinition(mapping);
	}
	
	
	public void testNextInChain() throws InterruptedException, ServiceException {
		ObjectClazz source = new ObjectClazz(this.common.getDefinition("INC"));
		ObjectClazz target = new ObjectClazz(this.common.getDefinition("INC"));

		this.context.addDefinition(source);
		this.context.addDefinition(target);
		this.context.addConnection(source.getId(), target.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		this.context.sync();
		
		Interpreter interpreter = new Interpreter(this.context, source);

		Message message = new Message();
		message.setPayload(0l);
		
		Thread thread = interpreter.run(message);
		
		assertEquals(2l, thread.getCurrentMessage().getPayload());
	}
	
	public void testChoiceRouter() throws ServiceException {
		ObjectClazz source = new ObjectClazz(this.common.getDefinition("INC"));
		ObjectClazz router = new ObjectClazz(this.common.getDefinition("CHOICE"));
		ObjectClazz inc2 = new ObjectClazz(this.common.getDefinition("INC2"));
		ObjectClazz inc3 = new ObjectClazz(this.common.getDefinition("INC3"));

		this.context.addDefinition(source);
		this.context.addDefinition(router);
		this.context.addDefinition(inc2);
		this.context.addDefinition(inc3);

		this.context.addConnection(source.getId(), router.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		String inc2ConnectionId = this.context.addConnection(router.getId(), inc2.getId(), ConnectionType.CHOICE_CONNECTION).getId();
		String inc3ConnectionId = this.context.addConnection(router.getId(), inc3.getId(), ConnectionType.CHOICE_CONNECTION).getId();
		
		
		ObjectClazz inc2Connection = this.context.getDefinition(inc2ConnectionId);
		inc2Connection.setProperty("expression", "message.properties['val'] == 'aaa'");
		ObjectClazz inc3Connection = this.context.getDefinition(inc3ConnectionId);
		inc3Connection.setProperty("expression", "message.properties['val'] == 'bbb'");
		
		this.context.sync();
		
		Interpreter interpreter = new Interpreter(this.context, source);

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
		ObjectClazz source = new ObjectClazz(this.common.getDefinition("INC"));
		ObjectClazz router = new ObjectClazz(this.common.getDefinition("ALL"));
		ObjectClazz inc2 = new ObjectClazz(this.common.getDefinition("INC2"));
		ObjectClazz inc3 = new ObjectClazz(this.common.getDefinition("INC3"));

		this.context.addDefinition(source);
		this.context.addDefinition(router);
		this.context.addDefinition(inc2);
		this.context.addDefinition(inc3);

		this.context.addConnection(source.getId(), router.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		String inc2ConnectionId = this.context.addConnection(router.getId(), inc2.getId(), ConnectionType.ALL_CONNECTION).getId();
		String inc3ConnectionId = this.context.addConnection(router.getId(), inc3.getId(), ConnectionType.ALL_CONNECTION).getId();
		
		
		ObjectClazz inc2Connection = this.context.getDefinition(inc2ConnectionId);
		inc2Connection.setProperty("targetExpression", "message.properties['inc2'] = result.payload");
		ObjectClazz inc3Connection = this.context.getDefinition(inc3ConnectionId);
		inc3Connection.setProperty("targetExpression", "message.properties['inc3'] = result.payload");
		
		this.context.sync();
		
		Interpreter interpreter = new Interpreter(this.context, source);

		Message message = new Message();
		message.setPayload(0l);
		
		Thread thread = interpreter.run(message);
		assertEquals(1l, thread.getCurrentMessage().getPayload());
		assertEquals(3l, thread.getCurrentMessage().getProperty("inc2"));
		assertEquals(4l, thread.getCurrentMessage().getProperty("inc3"));
	}

	public void testWireTapRouter() throws ServiceException, InterruptedException {
		ObjectClazz source = new ObjectClazz(this.common.getDefinition("INC"));
		ObjectClazz router = new ObjectClazz(this.common.getDefinition("WIRE_TAP"));
		ObjectClazz inc2 = new ObjectClazz(this.common.getDefinition("INC2"));
		ObjectClazz listener = new ObjectClazz(this.common.getDefinition("LISTENER"));

		this.context.addDefinition(source);
		this.context.addDefinition(router);
		this.context.addDefinition(inc2);
		this.context.addDefinition(listener);

		this.context.addConnection(source.getId(), router.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		this.context.addConnection(router.getId(), inc2.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		this.context.addConnection(router.getId(), listener.getId(), ConnectionType.WIRE_TAP_CONNECTION);

		this.context.sync();
		
		Interpreter interpreter = new Interpreter(this.context, source);

		Message message = new Message();
		message.setPayload(0l);
		
		assertNull(((ListenerProcessor) listener.getInstance()).getLastMessage());
		
		Thread thread = interpreter.run(message);
		assertEquals(3l, thread.getCurrentMessage().getPayload());

		java.lang.Thread.sleep(500);
		
		assertNotNull(((ListenerProcessor) listener.getInstance()).getLastMessage());

		assertEquals(1l, ((ListenerProcessor) listener.getInstance()).getLastMessage().getPayload());
	}
	
	public void testFlow() throws ServiceException {
		FlowDefinition incBy2 = new FlowDefinition("INC_BY_2");

		ObjectClazz source = new ObjectClazz(this.common.getDefinition("INC"));
		ObjectClazz target = new ObjectClazz(this.common.getDefinition("INC"));
		ObjectClazz sourceTarget = this.context.createConnection(source, target, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		incBy2.addDefinition(source);
		incBy2.addDefinition(target);
		incBy2.addDefinition(sourceTarget);
		incBy2.setStartingDefinition(source);

		
		FlowDefinition incBy6 = new FlowDefinition("INC_BY_6");

		ObjectClazz by6_1 = new ObjectClazz(incBy2);
		ObjectClazz by6_2 = new ObjectClazz(incBy2);
		ObjectClazz by6_3 = new ObjectClazz(incBy2);
		ObjectClazz by6_1_by6_2 = this.context.createConnection(by6_1, by6_2, ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		ObjectClazz by6_2_by6_3 = this.context.createConnection(by6_2, by6_3, ConnectionType.NEXT_IN_CHAIN_CONNECTION);

		incBy6.addDefinition(by6_1);
		incBy6.addDefinition(by6_2);
		incBy6.addDefinition(by6_3);
		incBy6.addDefinition(by6_1_by6_2);
		incBy6.addDefinition(by6_2_by6_3);
		incBy6.setStartingDefinition(by6_1);

		
		ObjectClazz add6 = new ObjectClazz(incBy6);
		this.context.addDefinition(add6);
		
		ObjectClazz inc3 = new ObjectClazz(this.common.getDefinition("INC3"));
		this.context.addDefinition(inc3);

		ObjectClazz add62 = new ObjectClazz(incBy6);
		this.context.addDefinition(add62);

		this.context.addConnection(add6.getId(), inc3.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		this.context.addConnection(inc3.getId(), add62.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		
		this.context.sync();
		
		Interpreter interpreter = new Interpreter(this.context, add6);

		Message message = new Message();
		message.setPayload(0l);

		Thread thread = interpreter.run(message);
		assertEquals(15l, thread.getCurrentMessage().getPayload());
	}
}
