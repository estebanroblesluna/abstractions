package com.modules.http;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.abstractions.api.Message;

public class DevHttpMessageSource extends AbstractHttpMessageSource {

	private Map<String, HttpMessageSource> messageSources;
	
	public DevHttpMessageSource() {
		this.messageSources = new ConcurrentHashMap<String, HttpMessageSource>();
	}
	
	public void addSource(HttpMessageSource source) {
		this.messageSources.put(source.getId(), source);
	}
	
	public Message newMessage(Message message) {
		String resolvedId = this.getId(message);
		HttpMessageSource messageSource = this.messageSources.get(resolvedId);
		if (messageSource != null) {
			return messageSource.newMessage(message);
		} else {
			return message;
		}
	}

	private String getId(Message message) {
		Object id = message.getProperty("actions.http." + HttpUtils.DEV_HTTP_PARAM);
		return id == null ? "" : id.toString();
	}
}
