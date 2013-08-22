package com.service.core;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class ContextHolder {
	private Cache<String, ContextDefinition> definitions;
	
	public ContextHolder(long duration, TimeUnit unit) {
		this.definitions = CacheBuilder
				.newBuilder()
				.concurrencyLevel(4)
				.maximumSize(10000)
				.expireAfterAccess(duration, unit)
				.removalListener(new RemovalListener<String, ContextDefinition>() {
					@Override
					public void onRemoval(RemovalNotification<String, ContextDefinition> notification) {
						notification.getValue().terminate();
					}
		}).build();
	}
	
	public ContextDefinition get(String id) {
		return this.definitions.getIfPresent(id);
	}
	
	public void put(ContextDefinition definition) {
		this.definitions.put(definition.getId(), definition);
	}
}
