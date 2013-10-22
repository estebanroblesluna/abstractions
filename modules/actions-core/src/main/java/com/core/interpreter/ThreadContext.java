package com.core.interpreter;

import com.abstractions.clazz.core.ObjectClazz;
import com.core.api.Message;
import com.service.core.ContextDefinition;

public class ThreadContext {

	private final ContextDefinition context;
	private final ObjectClazz processor;
	private final Message message;
	
	public ThreadContext(ContextDefinition context, ObjectClazz processor, Message message) {
		this.context = context;
		this.processor = processor;
		this.message = message;
	}

	public ObjectClazz getProcessor() {
		return processor;
	}

	public Message getMessage() {
		return message;
	}

	public ContextDefinition getContext() {
		return context;
	}
}
