package com.abstractions.meta;


public class MessageSourceDefinition extends ElementDefinition {

	protected MessageSourceDefinition() { }

	public MessageSourceDefinition(String name) {
		super(name);
	}

	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.MESSAGE_SOURCE;
	}
	
	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitMessageSourceDefinition(this);
	}
}
