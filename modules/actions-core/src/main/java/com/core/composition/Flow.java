package com.core.composition;

import com.core.api.Element;
import com.core.api.Evaluable;
import com.service.core.ContextDefinition;
import com.service.core.ObjectDefinition;

public class Flow implements Element, Evaluable {
	
	private ObjectDefinition starting;
	private ContextDefinition context;

	public ObjectDefinition getStarting() {
		return starting;
	}

	public void setStarting(ObjectDefinition starting) {
		this.starting = starting;
	}

	public ContextDefinition getContext() {
		return context;
	}

	public void setContext(ContextDefinition context) {
		this.context = context;
	}
}
