package com.core.api;

public interface Context {

	void addObject(String id, Object object);

	<T> T getObjectWithId(String id);

	<T> T deleteObjectWithId(String id);
}