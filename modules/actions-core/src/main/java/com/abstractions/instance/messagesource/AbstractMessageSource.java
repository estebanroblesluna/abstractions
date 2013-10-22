package com.abstractions.instance.messagesource;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.lang.Validate;

import com.abstractions.api.IdentificableMutable;
import com.abstractions.api.Message;

public class AbstractMessageSource implements MessageSource, IdentificableMutable {

	private String id;
	protected Set<MessageSourceListener> listeners;
	protected volatile MessageSourceListener mainListener;
	
	protected AbstractMessageSource() {
		this.listeners = new CopyOnWriteArraySet<MessageSourceListener>();
	}
	
	@Override
	public void addListener(MessageSourceListener listener) {
		Validate.notNull(listener);
		this.listeners.add(listener);
	}

	@Override
	public void removeListener(MessageSourceListener listener) {
		Validate.notNull(listener);
		this.listeners.remove(listener);
	}

	protected Message newMessage(Message message) {
		Message response = this.mainListener.onMessageReceived(this, message);
		
		for (MessageSourceListener listener : this.listeners) {
			listener.onMessageReceived(this, message);
		}
		
		return response;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setMainListener(MessageSourceListener listener) {
		this.mainListener = listener;
	}
}
