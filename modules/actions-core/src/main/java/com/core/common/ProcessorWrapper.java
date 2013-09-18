package com.core.common;

import org.jsoup.helper.Validate;

import com.core.api.Message;
import com.core.api.Processor;

public class ProcessorWrapper implements Processor {

	protected final Processor processor;
	
	protected ProcessorWrapper(Processor processor) {
		Validate.notNull(processor);
		this.processor = processor;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message process(Message message) {
		return this.processor.process(message);
	}
	
	public Processor getInstance() {
		return this.processor;
	}
}
