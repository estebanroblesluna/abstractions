package com.abstractions.meta;

import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.template.ElementTemplate;

public class ConnectionDefinition extends ElementDefinition {

	private String color;
	private String acceptedSourceTypes;
	private int acceptedSourceMax;
	private String acceptedTargetTypes;
	private int acceptedTargetMax;
	
	protected ConnectionDefinition() { }

	public ConnectionDefinition(String name) {
		super(name);
		this.acceptedSourceTypes = "";
		this.acceptedSourceMax = 0;
		this.acceptedTargetTypes = "";
		this.acceptedTargetMax = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evaluateUsing(Thread thread) {
		//DO NOTHING AS CONNECTIONS SHOULD NOT BE EVALUABLE
	}
	
	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.CONNECTION;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getAcceptedSourceTypes() {
		return acceptedSourceTypes;
	}

	public void setAcceptedSourceTypes(String acceptedSourceTypes) {
		this.acceptedSourceTypes = acceptedSourceTypes;
	}

	public int getAcceptedSourceMax() {
		return acceptedSourceMax;
	}

	public void setAcceptedSourceMax(int acceptedSourceMax) {
		this.acceptedSourceMax = acceptedSourceMax;
	}

	public String getAcceptedTargetTypes() {
		return acceptedTargetTypes;
	}

	public void setAcceptedTargetTypes(String acceptedTargetTypes) {
		this.acceptedTargetTypes = acceptedTargetTypes;
	}

	public int getAcceptedTargetMax() {
		return acceptedTargetMax;
	}

	public void setAcceptedTargetMax(int acceptedTargetMax) {
		this.acceptedTargetMax = acceptedTargetMax;
	}

	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitConnectionDefinition(this);
	}

	public ElementTemplate createInstance(ElementTemplate sourceDefinition, ElementTemplate targetDefinition) {
		ElementTemplate definition = new ElementTemplate(this);
		
		definition.setProperty("source", "urn:" + sourceDefinition.getId());
		definition.setProperty("target", "urn:" + targetDefinition.getId());
		definition.setProperty("type", this.getName());
		
		this.addOutgoingConnection(sourceDefinition, definition);
		this.addIncomingConnection(targetDefinition, definition);
		
		return definition;
	}

	public void addOutgoingConnection(ElementTemplate sourceDefinition, ElementTemplate definition) {
		sourceDefinition.addConnection(definition);
	}

	public void addIncomingConnection(ElementTemplate targetDefinition, ElementTemplate definition) {
		targetDefinition.addIncomingConnection(definition);
	}
}
