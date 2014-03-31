package com.abstractions.instance.common;

public abstract class AbstractElementInterceptor implements ElementInterceptor {

	private String elementId;
	
	@Override
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}

	@Override
	public String getElementId() {
		return this.elementId;
	}
}
