package com.abstractions.model;

public class ConnectionDefinition extends ElementDefinition {

	private String color;
	private String acceptedSourceTypes;
	private int acceptedSourceMax;
	private String acceptedTargetTypes;
	private int acceptedTargetMax;
	
	public ConnectionDefinition(String name) {
		super(name);
		this.acceptedSourceTypes = "";
		this.acceptedSourceMax = 0;
		this.acceptedTargetTypes = "";
		this.acceptedTargetMax = 0;
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
}
