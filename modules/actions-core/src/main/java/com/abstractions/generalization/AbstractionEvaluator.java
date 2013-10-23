package com.abstractions.generalization;

import com.abstractions.instance.composition.CompositeElementImpl;
import com.abstractions.meta.AbstractionDefinition;
import com.abstractions.runtime.interpreter.Evaluator;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class AbstractionEvaluator implements Evaluator {

	@Override
	public void evaluate(Thread thread) {
		CompositeElementImpl composite = (CompositeElementImpl) thread.getCurrentObjectDefinition().getInstance();
		CompositeTemplate compositeTemplate = composite.getTemplate();
		AbstractionDefinition abstractionDefinition = (AbstractionDefinition) thread.getCurrentObjectDefinition().getMeta();
		ElementTemplate startingElement = abstractionDefinition.getStartingDefinition();
		
		thread.pushCurrentContext(compositeTemplate);
		thread.setCurrentElement(startingElement);
	}
}
