package com.abstractions.model;


public class ProcessorDefinition extends ElementDefinition {

	protected ProcessorDefinition() { }

	public ProcessorDefinition(String name) {
		super(name);
	}

	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.PROCESSOR;
	}
	
	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitProcessorDefinition(this);
	}
}
