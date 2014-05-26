package com.abstractions.runtime.interpreter;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Message;
import com.abstractions.instance.core.AllConnection;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.service.core.BeanUtils;
import com.abstractions.template.ElementTemplate;

public class AllRouterEvaluator implements Evaluator {

	private static Log log = LogFactory.getLog(AllRouterEvaluator.class);

	@Override
	public void evaluate(Thread thread) {
		ElementTemplate currentElement = thread.getCurrentObjectDefinition();
		Message currentMessage = thread.getCurrentMessage();
		ExecutorService service = thread.getExecutorServiceFor(currentElement);
		
		String connections = currentElement.getProperty("__connections" + ConnectionType.ALL_CONNECTION.getElementName());
		List<String> urns = BeanUtils.getUrnsFromList(connections);
		
		int count = 0;
		for (String urn : urns) {
			final ElementTemplate connectionDefinition = thread.getComposite().resolve(urn);
			if (connectionDefinition != null) {
				count++;
			}
		}

		CountDownLatch latch = new CountDownLatch(count);
		
		for (String urn : urns) {
			final ElementTemplate connectionDefinition = thread.getComposite().resolve(urn);
			if (connectionDefinition != null) {
				Object connection = connectionDefinition.getInstance();
				if (connection instanceof AllConnection) {
	        String urnTarget = connectionDefinition.getProperty("target");
	        ElementTemplate targetDefinition = thread.getComposite().resolve(urnTarget);
	        AllConnection allConnection = (AllConnection) connectionDefinition.getInstance();

					thread.startSubthread(service, currentMessage.clone(), currentMessage, targetDefinition, allConnection.getTargetExpression(), latch);
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
