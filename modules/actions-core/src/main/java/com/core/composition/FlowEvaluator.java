package com.core.composition;

import com.abstractions.clazz.core.ObjectClazz;
import com.core.interpreter.Evaluator;
import com.core.interpreter.Thread;

public class FlowEvaluator implements Evaluator {

	@Override
	public void evaluate(Thread thread) {
		ObjectClazz flowDefinition = thread.getCurrentObjectDefinition();
		Flow flow = (Flow) flowDefinition.getInstance();
		ObjectClazz startingElement = flow.getStarting();
		
		thread.pushCurrentContext(flow.getContext());
		thread.setCurrentElement(startingElement);
	}
	
}
