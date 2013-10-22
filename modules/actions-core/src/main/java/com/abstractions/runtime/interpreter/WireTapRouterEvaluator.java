package com.abstractions.runtime.interpreter;

import java.util.concurrent.ExecutorService;

import com.abstractions.api.Message;
import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.service.core.ContextDefinition;

public class WireTapRouterEvaluator implements Evaluator {

	@Override
	public void evaluate(final Thread thread) {
		final Message clonedMessage = thread.getCurrentMessage().clone();
		final ContextDefinition context = thread.getContext();
		final ObjectClazz wireTap = thread.getCurrentObjectDefinition();
		
		thread.computeNextInChainProcessorAndSet();

		ExecutorService service = thread.getExecutorServiceFor(wireTap);
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				ObjectClazz connection = wireTap.getUniqueConnectionOfType(ConnectionType.WIRE_TAP_CONNECTION.getElementName(), context);
				ObjectClazz target = context.resolve(connection.getProperty("target"));
				Thread newThread = thread.getInterpreter().createThread(target, clonedMessage);
				newThread.run();
			}
		});		
	}
}
