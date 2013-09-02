package com.core.interpreter;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.core.api.Message;
import com.core.impl.AllConnection;
import com.core.impl.ConnectionType;
import com.service.core.BeanUtils;
import com.service.core.ObjectDefinition;

public class AllRouterEvaluator implements Evaluator {

	private static Log log = LogFactory.getLog(AllRouterEvaluator.class);

	@Override
	public void evaluate(Thread thread) {
		ObjectDefinition currentElement = thread.getCurrentObjectDefinition();
		Message currentMessage = thread.getCurrentMessage();
		ExecutorService service = thread.getExecutorServiceFor(currentElement);
		
		String connections = currentElement.getProperty("__connections" + ConnectionType.ALL_CONNECTION.getElementName());
		List<String> urns = BeanUtils.getUrnsFromList(connections);
		CountDownLatch latch = new CountDownLatch(urns.size());
		
		for (String urn : urns) {
			final ObjectDefinition connectionDefinition = thread.getContext().resolve(urn);
			Object connection = connectionDefinition.getInstance();
			if (connection instanceof AllConnection) {
				thread.startSubthread(service, currentMessage.clone(), currentMessage, connectionDefinition, latch);
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
