package com.core.common;

import org.jsoup.helper.Validate;

import com.core.api.Message;
import com.core.api.Processor;

public class ProcessorWrapper implements Processor {

	protected volatile Processor processor;
	
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
	
	public Processor unwrap(Class<?> theClass) {
		Processor processor = this.processor;
		
		if (processor instanceof ProcessorWrapper) {
			ProcessorWrapper wrapper = (ProcessorWrapper) processor;
			processor = wrapper.unwrap(theClass);
		}
		
		this.processor = processor;
		
		if (theClass.isAssignableFrom(this.getClass())) {
			return processor;
		} else {
			return this;
		}
	}
	
	public boolean isWrapWith(Class<?> theClass) {
		boolean isWrap = false;
		
		if (this.processor instanceof ProcessorWrapper) {
			ProcessorWrapper wrapper = (ProcessorWrapper) this.processor;
			isWrap = isWrap || wrapper.isWrapWith(theClass);
		}
		
		isWrap = isWrap || theClass.isAssignableFrom(this.getClass());
		
		return isWrap;
	}

}
