package com.abstractions.runtime.interpreter;

import java.util.List;

import com.abstractions.api.Message;
import com.abstractions.instance.core.ChoiceConnection;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.service.core.BeanUtils;
import com.abstractions.template.ElementTemplate;

public class ChoiceRouterEvaluator implements Evaluator {

	@Override
	public void evaluate(Thread thread) {
		//message is the same so update the current processor
		ElementTemplate newDefinition = this.getChoiceObjectDefinition(thread.getCurrentObjectDefinition(), thread);
		
		if (newDefinition != null) {
			thread.pushCurrentContext();
			String targetId = newDefinition.getProperty("target");
			ElementTemplate target = thread.getComposite().resolve(targetId);
			thread.setCurrentElement(target);
		} else {
			thread.computeNextInChainProcessorAndSet();
		}		
	}
	
	private ElementTemplate getChoiceObjectDefinition(ElementTemplate choiceRouter, Thread thread) {
		//this is a choice processor so get the connections property
		Message currentMessage = thread.getCurrentMessage();
		
		String connections = choiceRouter.getProperty("__connections" + ConnectionType.CHOICE_CONNECTION.getElementName());
		List<String> urns = BeanUtils.getUrnsFromList(connections);
		for (String urn : urns) {
			ElementTemplate connectionDefinition = thread.getComposite().resolve(urn);
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
