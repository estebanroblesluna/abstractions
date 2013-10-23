package com.abstractions.api;

public interface CompositeElement extends Element, Startable, Terminable {

	void addObject(String id, Element object);

	<T> T getObjectWithId(String id);

	<T> T deleteObjectWithId(String id);
}