package com.abstractions.meta;

import com.abstractions.runtime.interpreter.Thread;


public class MessageSourceDefinition extends ElementDefinition {

	protected MessageSourceDefinition() { }

	public MessageSourceDefinition(String name) {
		super(name);
	}

	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.MESSAGE_SOURCE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evaluateUsing(Thread thread) {
		//DO NOTHING AS MESSAGE SOURCES SHOULD NOT BE EVALUABLE
	}
	
	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitMessageSourceDefinition(this);
	}
}
