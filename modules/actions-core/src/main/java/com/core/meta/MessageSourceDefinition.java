package com.core.meta;

public class MessageSourceDefinition extends ElementDefinition {

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
