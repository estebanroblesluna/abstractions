package com.service.core;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class DevelopmentContextHolder {
	
	private Cache<String, ContextDefinition> definitions;
	private KeyProvider keyProvider;
	
	public DevelopmentContextHolder(long duration, TimeUnit unit) {
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
		
		this.keyProvider = new NullKeyProvider();
	}
	
	public ContextDefinition get(String id) {
		String key = this.keyProvider.apply(id);
		return this.definitions.getIfPresent(key);
	}
	
	public void put(ContextDefinition definition) {
		String key = this.keyProvider.apply(definition.getId());
		this.definitions.put(key, definition);
	}

	public KeyProvider getKeyProvider() {
		return keyProvider;
	}

	public void setKeyProvider(KeyProvider keyProvider) {
		this.keyProvider = keyProvider;
	}
}
