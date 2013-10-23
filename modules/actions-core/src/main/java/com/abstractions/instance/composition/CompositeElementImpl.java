package com.abstractions.instance.composition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.api.Identificable;
import com.abstractions.api.Startable;
import com.abstractions.api.Terminable;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.utils.IdGenerator;

public class CompositeElementImpl implements CompositeElement, Identificable {

	private final String id;
	private final Map<String, Element> objects;
	private final CompositeTemplate template;
	
	public CompositeElementImpl(CompositeTemplate template)
	{
		this.template = template;
		this.id = IdGenerator.getNewId();
		this.objects = new ConcurrentHashMap<String, Element>();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addObject(String id, Element object) {
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void terminate() {
		for (Element element : this.objects.values()) {
			if (element instanceof Terminable) {
				((Terminable) element).terminate();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		for (Element element : this.objects.values()) {
			if (element instanceof Startable) {
				((Startable) element).start();
			}
		}
	}

	public CompositeTemplate getTemplate() {
		return template;
	}
}
