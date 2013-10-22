package com.abstractions.instance.messagesource;

import com.abstractions.api.Message;

public interface MessageSourceListener {

	Message onMessageReceived(MessageSource messageSource, Message message);
	
}
