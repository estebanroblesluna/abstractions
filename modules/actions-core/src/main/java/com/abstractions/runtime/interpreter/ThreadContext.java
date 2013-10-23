package com.abstractions.runtime.interpreter;

import com.abstractions.api.Message;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class ThreadContext {

	private final CompositeTemplate composite;
	private final ElementTemplate processor;
	private final Message message;
	
	public ThreadContext(CompositeTemplate composite, ElementTemplate processor, Message message) {
		this.composite = composite;
		this.processor = processor;
		this.message = message;
	}

	public ElementTemplate getProcessor() {
		return processor;
	}

	public Message getMessage() {
		return message;
	}

	public CompositeTemplate getComposite() {
		return composite;
	}
}
