package com.core.composition;

import com.core.interpreter.Evaluator;
import com.core.interpreter.Thread;
import com.service.core.ObjectDefinition;

public class FlowEvaluator implements Evaluator {

	@Override
	public void evaluate(Thread thread) {
		ObjectDefinition flowDefinition = thread.getCurrentObjectDefinition();
		Flow flow = (Flow) flowDefinition.getInstance();
		ObjectDefinition startingElement = flow.getStarting();
		
		thread.pushCurrentContext(flow.getContext());
		thread.setCurrentElement(startingElement);
	}
	
}
