package com.modules.dust.store;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryIndexedKeyValueStore<T> implements IndexedKeyValueStore<T> {

	Map<String, Map<String, T>> indexes;

	public InMemoryIndexedKeyValueStore() {
		this.indexes = new ConcurrentHashMap<String, Map<String, T>>();
	}

	@Override
	public void put(String index, String key, T object) {
		if (!this.indexes.containsKey(index)) {
			this.indexes.put(index, new ConcurrentHashMap<String, T>());
		}
		this.indexes.get(index).put(key, object);
	}

	@Override
	public T get(String index, String key) {
		if (!this.indexes.containsKey(index)) {
			return null;
		}
		return this.indexes.get(index).get(key);
	}

	@Override
	public Collection<T> getFromIndex(String index) {
		if (!this.indexes.containsKey(index)) {
			return null;
		}
		return this.indexes.get(index).values();
	}

	@Override
	public void remove(String index, String key) {
		if (!this.indexes.containsKey(index)) {
			return;
		}
		this.indexes.get(index).remove(key);
	}
}
