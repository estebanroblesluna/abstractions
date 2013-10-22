package com.abstractions.instance.common;

import com.abstractions.api.Message;
import com.abstractions.api.Processor;

public class ListenerProcessor implements Processor {

	private Message lastMessage;
	
	@Override
	public Message process(Message message) {
		this.lastMessage = message;
		return message;
	}

	public Message getLastMessage() {
		return lastMessage;
	}
}
