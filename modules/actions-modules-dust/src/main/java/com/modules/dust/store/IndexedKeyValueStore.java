package com.modules.dust.store;

import java.util.Collection;

public interface IndexedKeyValueStore<T> {
	
	void put(String index, String key, T object);
	
	T get(String index, String key);
	
	void remove(String index, String key);
	
	Collection<T> getFromIndex(String index);

}
