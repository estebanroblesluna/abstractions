package com.abstractions.instance.common;

import com.core.api.Message;
import com.core.api.Processor;
import com.core.utils.Average;

public class PerformanceProcessor extends ProcessorWrapper {

	private final Average average;
	
	public PerformanceProcessor(Processor processor) {
		super(processor);
		this.average = new Average();
	}

	@Override
	public Message process(Message message) {
		long before = System.currentTimeMillis();
		
		Message result = super.process(message);
		
		long after = System.currentTimeMillis();
		
		this.average.append(after - before);
		
		return result;
	}
	
	public double getAverage() {
		return this.average.average();
	}
}
