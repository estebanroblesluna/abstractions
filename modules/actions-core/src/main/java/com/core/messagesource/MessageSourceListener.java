package com.core.messagesource;

import com.core.api.Message;

public interface MessageSourceListener {

	Message onMessageReceived(MessageSource messageSource, Message message);
	
}
