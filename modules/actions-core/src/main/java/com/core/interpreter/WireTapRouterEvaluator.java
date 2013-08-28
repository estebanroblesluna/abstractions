package com.core.interpreter;

import java.util.concurrent.ExecutorService;

import com.core.api.Message;
import com.core.impl.ConnectionType;
import com.service.core.ContextDefinition;
import com.service.core.ObjectDefinition;

public class WireTapRouterEvaluator implements RouterEvaluator {

	@Override
	public void evaluate(final Thread thread) {
		final Message clonedMessage = thread.getCurrentMessage().clone();
		final ContextDefinition context = thread.getInterpreter().getContext();
		final ObjectDefinition wireTap = thread.getCurrentObjectDefinition();
		
		thread.computeNextInChainProcessorAndSet();

		ExecutorService service = thread.getExecutorServiceFor(wireTap);
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				ObjectDefinition connection = wireTap.getUniqueConnectionOfType(ConnectionType.WIRE_TAP_CONNECTION.getElementName(), context);
				ObjectDefinition target = context.resolve(connection.getProperty("target"));
				Thread newThread = thread.getInterpreter().createThread(target, clonedMessage);
				newThread.run();
			}
		});		
	}
}
