package com.abstractions.service.core;

import com.abstractions.api.Message;
import com.abstractions.api.Processor;

public class IncrementProcessor implements Processor {
	
	private long increment = 1;
	
	@Override
	public Message process(Message message) {
		long number = (Long) message.getPayload();
		number = number + increment;
		message.setPayload(number);
		
		return message;
	}

	public long getIncrement() {
		return increment;
	}

	public void setIncrement(long increment) {
		this.increment = increment;
	}
}