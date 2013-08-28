package com.service.core;

import junit.framework.TestCase;

import com.core.api.Message;
import com.core.common.NullProcessor;
import com.core.impl.ConnectionType;
import com.core.impl.NextInChainConnection;
import com.core.interpreter.Interpreter;

public class ContextPerfTest extends TestCase {

	private ContextDefinition context;
	private String startId;
	private NamesMapping mapping;
	
	public void testPerf() throws ServiceException {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		mapping = new NamesMapping();
		mapping.addMapping("A", IncrementProcessor.class);
		mapping.addMapping("B", NullProcessor.class);
		mapping.addMapping("NEXT_IN_CHAIN_CONNECTION", NextInChainConnection.class);
		long count = 10000;

		this.context = new ContextDefinition(this.mapping);
		
		long start = System.currentTimeMillis();
		
		this.buildChain(count, "A");
		String startIdA = this.startId;

		this.buildChain(count, "B");
		String startIdB = this.startId;

		this.context.sync();
		long end = System.currentTimeMillis();

		System.out.println("Took " + (end - start) + "ms to build chain A and B");
		
		//evaluate A
		Message message = new Message();
		message.setPayload(0l);
		
		ObjectDefinition source = context.getDefinition(startIdA);
		Interpreter interpreter = new Interpreter(context, source);
		com.core.interpreter.Thread thread = interpreter.createThread(source, message);
		
		start = System.currentTimeMillis();
		thread.run();
		end = System.currentTimeMillis();
		long chainA = (end - start);
		
		System.out.println("Took " + chainA + "ms to run " + count + " in chain A");
		
		assertEquals(count, thread.getCurrentMessage().getPayload());

		//evaluate B
		message = new Message();
		message.setPayload(0l);
		
		source = context.getDefinition(startIdB);
		interpreter = new Interpreter(context, source);
		thread = interpreter.createThread(source, message);
		
		start = System.currentTimeMillis();
		thread.run();
		end = System.currentTimeMillis();
		long overhead = (end - start);
		
		System.out.println("Took " + overhead + "ms to run " + count + " in chain B");

		System.out.println("Overhead of actions in chain A " + overhead);
		System.out.println("Processing time of actions in chain A " + (chainA - overhead));
	}

	private void buildChain(long count, String elementName) {
		ObjectDefinition source = null;
		for (int i = 0; i < count; i++) {
			ObjectDefinition definition = new ObjectDefinition(elementName);
			
			this.context.addDefinition(definition);
			
			if (source == null) {
				this.startId = definition.getId();
			} else {
				this.context.addConnection(source.getId(), definition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
			}

			source = definition;
		}
	}
}
