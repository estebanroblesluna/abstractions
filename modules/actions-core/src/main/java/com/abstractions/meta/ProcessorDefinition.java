package com.abstractions.meta;

import com.abstractions.api.Message;
import com.abstractions.api.Processor;
import com.abstractions.runtime.interpreter.Thread;

public class ProcessorDefinition extends ElementDefinition {

	protected ProcessorDefinition() { }

	public ProcessorDefinition(String name) {
		super(name);
	}

	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.PROCESSOR;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evaluateUsing(Thread thread) {
		Message currentMessage = thread.getCurrentMessage();
		Processor processor = (Processor) thread.getCurrentElement();
		
		currentMessage = processor.process(currentMessage);
		
		thread.setCurrentMessage(currentMessage);
		thread.computeNextInChainProcessorAndSet();
	}
	
	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitProcessorDefinition(this);
	}
}
