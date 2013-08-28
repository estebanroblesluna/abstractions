package com.core.interpreter;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.common.expression.ScriptingLanguage;
import com.core.api.Message;
import com.core.common.ListenerProcessor;
import com.core.impl.AllConnection;
import com.core.impl.ChoiceConnection;
import com.core.impl.ConnectionType;
import com.core.impl.NextInChainConnection;
import com.core.impl.WireTapConnection;
import com.core.routing.AllRouter;
import com.core.routing.ChoiceRouter;
import com.core.routing.WireTapRouter;
import com.service.core.ContextDefinition;
import com.service.core.IncrementProcessor;
import com.service.core.NamesMapping;
import com.service.core.ObjectDefinition;
import com.service.core.ServiceException;

public class InterpreterTestCase extends TestCase {

	private ContextDefinition context;

	public void setUp() {
		NamesMapping mapping = new NamesMapping();
		
		mapping.addLanguage("groovy", ScriptingLanguage.GROOVY);
		
		mapping.addMapping("INC", IncrementProcessor.class);
		mapping.addMapping("INC2", IncrementProcessor.class, this.incrementMap(2l));
		mapping.addMapping("INC3", IncrementProcessor.class, this.incrementMap(3l));
		mapping.addMapping("LISTENER", ListenerProcessor.class);

		mapping.addMapping("CHOICE_ROUTER", ChoiceRouter.class);
		mapping.addEvaluator("CHOICE_ROUTER", new ChoiceRouterEvaluator());

		mapping.addMapping("ALL_ROUTER", AllRouter.class);
		mapping.addEvaluator("ALL_ROUTER", new AllRouterEvaluator());

		mapping.addMapping("WIRE_TAP_ROUTER", WireTapRouter.class);
		mapping.addEvaluator("WIRE_TAP_ROUTER", new WireTapRouterEvaluator());

		mapping.addMapping(ConnectionType.NEXT_IN_CHAIN_CONNECTION.getElementName(), NextInChainConnection.class);
		mapping.addMapping(ConnectionType.ALL_CONNECTION.getElementName(), AllConnection.class);
		mapping.addMapping(ConnectionType.CHOICE_CONNECTION.getElementName(), ChoiceConnection.class);
		mapping.addMapping(ConnectionType.WIRE_TAP_CONNECTION.getElementName(), WireTapConnection.class);

		this.context = new ContextDefinition(mapping);
	}
	
	
	private Map<String, String> incrementMap(Long l) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("increment", l.toString());
		return map;
	}

	public void testNextInChain() throws InterruptedException, ServiceException {
		ObjectDefinition source = new ObjectDefinition("INC");
		ObjectDefinition target = new ObjectDefinition("INC");

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
		ObjectDefinition source = new ObjectDefinition("INC");
		ObjectDefinition router = new ObjectDefinition("CHOICE_ROUTER");
		ObjectDefinition inc2 = new ObjectDefinition("INC2");
		ObjectDefinition inc3 = new ObjectDefinition("INC3");

		this.context.addDefinition(source);
		this.context.addDefinition(router);
		this.context.addDefinition(inc2);
		this.context.addDefinition(inc3);

		this.context.addConnection(source.getId(), router.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		String inc2ConnectionId = this.context.addConnection(router.getId(), inc2.getId(), ConnectionType.CHOICE_CONNECTION);
		String inc3ConnectionId = this.context.addConnection(router.getId(), inc3.getId(), ConnectionType.CHOICE_CONNECTION);
		
		
		ObjectDefinition inc2Connection = this.context.getDefinition(inc2ConnectionId);
		inc2Connection.setProperty("expression", "message.properties['val'] == 'aaa'");
		ObjectDefinition inc3Connection = this.context.getDefinition(inc3ConnectionId);
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
		ObjectDefinition source = new ObjectDefinition("INC");
		ObjectDefinition router = new ObjectDefinition("ALL_ROUTER");
		ObjectDefinition inc2 = new ObjectDefinition("INC2");
		ObjectDefinition inc3 = new ObjectDefinition("INC3");

		this.context.addDefinition(source);
		this.context.addDefinition(router);
		this.context.addDefinition(inc2);
		this.context.addDefinition(inc3);

		this.context.addConnection(source.getId(), router.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		String inc2ConnectionId = this.context.addConnection(router.getId(), inc2.getId(), ConnectionType.ALL_CONNECTION);
		String inc3ConnectionId = this.context.addConnection(router.getId(), inc3.getId(), ConnectionType.ALL_CONNECTION);
		
		
		ObjectDefinition inc2Connection = this.context.getDefinition(inc2ConnectionId);
		inc2Connection.setProperty("targetExpression", "message.properties['inc2'] = result.payload");
		ObjectDefinition inc3Connection = this.context.getDefinition(inc3ConnectionId);
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
		ObjectDefinition source = new ObjectDefinition("INC");
		ObjectDefinition router = new ObjectDefinition("WIRE_TAP_ROUTER");
		ObjectDefinition inc2 = new ObjectDefinition("INC2");
		ObjectDefinition listener = new ObjectDefinition("LISTENER");

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
}
