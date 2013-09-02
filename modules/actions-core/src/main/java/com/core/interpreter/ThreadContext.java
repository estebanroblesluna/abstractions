package com.core.interpreter;

import com.core.api.Message;
import com.service.core.ContextDefinition;
import com.service.core.ObjectDefinition;

public class ThreadContext {

	private final ContextDefinition context;
	private final ObjectDefinition processor;
	private final Message message;
	
	public ThreadContext(ContextDefinition context, ObjectDefinition processor, Message message) {
		this.context = context;
		this.processor = processor;
		this.message = message;
	}

	public ObjectDefinition getProcessor() {
		return processor;
	}

	public Message getMessage() {
		return message;
	}

	public ContextDefinition getContext() {
		return context;
	}
}
