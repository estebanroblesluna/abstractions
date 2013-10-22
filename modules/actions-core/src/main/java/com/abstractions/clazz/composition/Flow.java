package com.abstractions.clazz.composition;

import com.abstractions.api.Element;
import com.abstractions.api.Evaluable;
import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.service.core.ContextDefinition;

public class Flow implements Element, Evaluable {
	
	private ObjectClazz starting;
	private ContextDefinition context;

	public ObjectClazz getStarting() {
		return starting;
	}

	public void setStarting(ObjectClazz starting) {
		this.starting = starting;
	}

	public ContextDefinition getContext() {
		return context;
	}

	public void setContext(ContextDefinition context) {
		this.context = context;
	}
}
