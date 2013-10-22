package com.core.composition;

import com.abstractions.clazz.core.ObjectClazz;
import com.core.api.Element;
import com.core.api.Evaluable;
import com.service.core.ContextDefinition;

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
