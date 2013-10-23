package com.abstractions.runtime.interpreter;

import java.util.concurrent.ExecutorService;

import com.abstractions.api.Message;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class WireTapRouterEvaluator implements Evaluator {

	@Override
	public void evaluate(final Thread thread) {
		final Message clonedMessage = thread.getCurrentMessage().clone();
		final CompositeTemplate composite = thread.getComposite();
		final ElementTemplate wireTap = thread.getCurrentObjectDefinition();
		
		thread.computeNextInChainProcessorAndSet();

		ExecutorService service = thread.getExecutorServiceFor(wireTap);
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				ElementTemplate connection = wireTap.getUniqueConnectionOfType(ConnectionType.WIRE_TAP_CONNECTION.getElementName(), composite);
				ElementTemplate target = composite.resolve(connection.getProperty("target"));
				Thread newThread = thread.getInterpreter().createThread(target, clonedMessage);
				newThread.run();
			}
		});		
	}
}
