package com.core.interpreter;

import com.core.api.Message;
import com.service.core.ObjectDefinition;

public class ThreadContext {

	private final ObjectDefinition processor;
	private final Message message;
	
	public ThreadContext(ObjectDefinition processor, Message message) {
		this.processor = processor;
		this.message = message;
	}

	public ObjectDefinition getProcessor() {
		return processor;
	}

	public Message getMessage() {
		return message;
	}
}
