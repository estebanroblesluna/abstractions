package com.abstractions.runtime.interpreter;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Message;
import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.instance.core.AllConnection;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.service.core.BeanUtils;

public class AllRouterEvaluator implements Evaluator {

	private static Log log = LogFactory.getLog(AllRouterEvaluator.class);

	@Override
	public void evaluate(Thread thread) {
		ObjectClazz currentElement = thread.getCurrentObjectDefinition();
		Message currentMessage = thread.getCurrentMessage();
		ExecutorService service = thread.getExecutorServiceFor(currentElement);
		
		String connections = currentElement.getProperty("__connections" + ConnectionType.ALL_CONNECTION.getElementName());
		List<String> urns = BeanUtils.getUrnsFromList(connections);
		
		int count = 0;
		for (String urn : urns) {
			final ObjectClazz connectionDefinition = thread.getContext().resolve(urn);
			if (connectionDefinition != null) {
				count++;
			}
		}

		CountDownLatch latch = new CountDownLatch(count);
		
		for (String urn : urns) {
			final ObjectClazz connectionDefinition = thread.getContext().resolve(urn);
			if (connectionDefinition != null) {
				Object connection = connectionDefinition.getInstance();
				if (connection instanceof AllConnection) {
					thread.startSubthread(service, currentMessage.clone(), currentMessage, connectionDefinition, latch);
				}
			}
		}
		
	    try {
			latch.await();
			thread.computeNextInChainProcessorAndSet();
		} catch (InterruptedException e) {
			log.warn("Interrupting thread", e);
		}
	}
}
