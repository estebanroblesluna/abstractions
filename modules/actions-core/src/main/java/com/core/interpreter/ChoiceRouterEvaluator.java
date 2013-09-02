package com.core.interpreter;

import java.util.List;

import com.core.api.Message;
import com.core.impl.ChoiceConnection;
import com.core.impl.ConnectionType;
import com.service.core.BeanUtils;
import com.service.core.ObjectDefinition;

public class ChoiceRouterEvaluator implements Evaluator {

	@Override
	public void evaluate(Thread thread) {
		//message is the same so update the current processor
		ObjectDefinition newDefinition = this.getChoiceObjectDefinition(thread.getCurrentObjectDefinition(), thread);
		
		if (newDefinition != null) {
			thread.pushCurrentContext();
			String targetId = newDefinition.getProperty("target");
			ObjectDefinition target = thread.getContext().resolve(targetId);
			thread.setCurrentElement(target);
		} else {
			thread.computeNextInChainProcessorAndSet();
		}		
	}
	
	private ObjectDefinition getChoiceObjectDefinition(ObjectDefinition choiceRouter, Thread thread) {
		//this is a choice processor so get the connections property
		Message currentMessage = thread.getCurrentMessage();
		
		String connections = choiceRouter.getProperty("__connections" + ConnectionType.CHOICE_CONNECTION.getElementName());
		List<String> urns = BeanUtils.getUrnsFromList(connections);
		for (String urn : urns) {
			ObjectDefinition connectionDefinition = thread.getContext().resolve(urn);
			//TODO this should NOT occur unless there is UI problem while deleting connections
			if (connectionDefinition != null) {
				Object connection = connectionDefinition.getInstance();
				if (connection instanceof ChoiceConnection) {
					ChoiceConnection choiceConnection = (ChoiceConnection) connection;
					Object result = choiceConnection.getExpression().evaluate(currentMessage);
					if (result instanceof Boolean && ((Boolean) result)) {
						return connectionDefinition;
					}
				}
			}
		}
		
		return null;
	}
}
