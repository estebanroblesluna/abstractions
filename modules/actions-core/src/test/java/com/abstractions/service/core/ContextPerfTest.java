package com.abstractions.service.core;

import junit.framework.TestCase;

import com.abstractions.api.Message;
import com.abstractions.instance.common.NullProcessor;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.example.Meta;
import com.abstractions.model.Library;
import com.abstractions.runtime.interpreter.Interpreter;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class ContextPerfTest extends TestCase {

	private CompositeTemplate application;
	private String startId;
	private NamesMapping mapping;
	private Library common;
	
	public void testPerf() throws ServiceException {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

		mapping = new NamesMapping();
		
		common = Meta.getCommonLibrary();
		common.addBasicDefinitionForClass("A", IncrementProcessor.class);
		common.addBasicDefinitionForClass("B", NullProcessor.class);
		common.createMappings(mapping);
		
		long count = 10000;

		this.application = new CompositeTemplate(new ApplicationDefinition("myApp"), this.mapping);
		
		long start = System.currentTimeMillis();
		
		this.buildChain(count, "A");
		String startIdA = this.startId;

		this.buildChain(count, "B");
		String startIdB = this.startId;

		this.application.sync();
		long end = System.currentTimeMillis();

		System.out.println("Took " + (end - start) + "ms to build chain A and B");
		
		//evaluate A
		Message message = new Message();
		message.setPayload(0l);
		
		ElementTemplate source = application.getDefinition(startIdA);
		Interpreter interpreter = new Interpreter(application, source);
		com.abstractions.runtime.interpreter.Thread thread = interpreter.createThread(source, message);
		
		start = System.currentTimeMillis();
		thread.run();
		end = System.currentTimeMillis();
		long chainA = (end - start);
		
		System.out.println("Took " + chainA + "ms to run " + count + " in chain A");
		
		assertEquals(count, thread.getCurrentMessage().getPayload());

		//evaluate B
		message = new Message();
		message.setPayload(0l);
		
		source = application.getDefinition(startIdB);
		interpreter = new Interpreter(application, source);
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
		ElementTemplate source = null;
		for (int i = 0; i < count; i++) {
			ElementTemplate definition = new ElementTemplate(common.getDefinition(elementName));
			
			this.application.addDefinition(definition);
			
			if (source == null) {
				this.startId = definition.getId();
			} else {
				this.application.addConnection(source.getId(), definition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
			}

			source = definition;
		}
	}
}
