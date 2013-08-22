package com.core.common;

import com.core.api.Message;
import com.core.api.Processor;

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
