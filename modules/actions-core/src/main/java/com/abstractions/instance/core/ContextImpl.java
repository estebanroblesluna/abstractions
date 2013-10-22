package com.abstractions.instance.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.abstractions.api.Context;
import com.abstractions.api.Terminable;
import com.abstractions.utils.IdGenerator;

public class ContextImpl implements Terminable, Context {

	private final String id;
	private final Map<String, Object> objects;
	
	public ContextImpl()
	{
		this.id = IdGenerator.getNewId();
		this.objects = new ConcurrentHashMap<String, Object>();
	}
	
	public String getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObject(String id, Object object) {
		this.objects.put(id, object);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getObjectWithId(String id) {
		return (T) this.objects.get(id);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T deleteObjectWithId(String id) {
		return (T) this.objects.remove(id);
	}

	@Override
	public void terminate() {
	}
}
