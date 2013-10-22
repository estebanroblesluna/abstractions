package com.abstractions.runtime.interpreter;

import java.util.List;

import com.abstractions.api.Message;
import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.instance.core.ChoiceConnection;
import com.abstractions.instance.core.ConnectionType;
import com.service.core.BeanUtils;

public class ChoiceRouterEvaluator implements Evaluator {

	@Override
	public void evaluate(Thread thread) {
		//message is the same so update the current processor
		ObjectClazz newDefinition = this.getChoiceObjectDefinition(thread.getCurrentObjectDefinition(), thread);
		
		if (newDefinition != null) {
			thread.pushCurrentContext();
			String targetId = newDefinition.getProperty("target");
			ObjectClazz target = thread.getContext().resolve(targetId);
			thread.setCurrentElement(target);
		} else {
			thread.computeNextInChainProcessorAndSet();
		}		
	}
	
	private ObjectClazz getChoiceObjectDefinition(ObjectClazz choiceRouter, Thread thread) {
		//this is a choice processor so get the connections property
		Message currentMessage = thread.getCurrentMessage();
		
		String connections = choiceRouter.getProperty("__connections" + ConnectionType.CHOICE_CONNECTION.getElementName());
		List<String> urns = BeanUtils.getUrnsFromList(connections);
		for (String urn : urns) {
			ObjectClazz connectionDefinition = thread.getContext().resolve(urn);
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
