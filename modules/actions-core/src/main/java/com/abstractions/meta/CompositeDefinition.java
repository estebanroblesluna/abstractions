package com.abstractions.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.api.IdentificableMutable;
import com.abstractions.instance.composition.CompositeElementImpl;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public abstract class CompositeDefinition extends ElementDefinition {

	private static final Log log = LogFactory.getLog(CompositeDefinition.class);

	private Map<String, ElementTemplate> definitions;
	
	protected CompositeDefinition() { }

	protected CompositeDefinition(String name) {
		super(name);
		this.definitions = new HashMap<String, ElementTemplate>();
	}

	public void addDefinition(ElementTemplate definition) {
		this.definitions.put(definition.getId(), definition);
	}

	public void addDefinitions(Collection<ElementTemplate> definitions) {
		for (ElementTemplate definition : definitions) {
			this.addDefinition(definition);
		}
	}

	public Map<String, ElementTemplate> getDefinitions() {
		return Collections.unmodifiableMap(this.definitions);
	}

	@Override
	public Element instantiate(CompositeElement container, NamesMapping mapping, ElementTemplate template) throws InstantiationException, IllegalAccessException {
		CompositeTemplate compositeTemplate = (CompositeTemplate) template;
		CompositeElementImpl compositeElement = this.basicCreateInstance(compositeTemplate);
		compositeTemplate.overrideObject(compositeElement);
		
		synchronized (compositeTemplate.getDefinitions()) {
			for (ElementTemplate elementTemplate : compositeTemplate.getDefinitions().values()) {
				try {
					if (!elementTemplate.isInstantiated()) {
						Element element = elementTemplate.instantiate(compositeElement, mapping);
						if (element instanceof IdentificableMutable) {
							((IdentificableMutable) element).setId(elementTemplate.getId());
						}
						compositeTemplate.afterInstantiation(element, elementTemplate);
						compositeElement.addObject(elementTemplate.getId(), element);
					}
					compositeTemplate.afterScan(elementTemplate.getInstance(), elementTemplate);
				} catch (ServiceException e) {
					log.warn("Error instantiating object", e);
				}
			}
		}

		return compositeElement;
	}
	
	protected CompositeElementImpl basicCreateInstance(CompositeTemplate compositeTemplate) {
		return new CompositeElementImpl(compositeTemplate);
	}

	public void initialize(ElementTemplate template, Map<String, String> properties, CompositeElement container, NamesMapping mapping) {
		super.initialize(template, properties, container, mapping);
		
		CompositeTemplate composite = (CompositeTemplate) template;
		CompositeElement compositeElement = composite.getCompositeElement();
		
		synchronized (composite.getDefinitions()) {
			//wire them all together
			for (ElementTemplate definition : composite.getDefinitions().values())
			{
				try {
					definition.initialize(compositeElement, mapping);
				} catch (ServiceException e) {
					log.warn("Error initializing object", e);
				}
			}		
		}
	}

	public CompositeTemplate createTemplate(NamesMapping mapping) {
		return this.createTemplate(null, mapping);
	}

	public CompositeTemplate createTemplate(String id, NamesMapping mapping) {
		CompositeTemplate applicationTemplate = this.basicCreateTemplate(id, this, mapping);

		List<ElementTemplate> connections = new ArrayList<ElementTemplate>();
		for (ElementTemplate element : this.getDefinitions().values()) {
			if (element.getMeta().isConnection()) {
				connections.add(element);
			} else {
				applicationTemplate.addDefinition(element);
			}
		}
		
		for (ElementTemplate element : connections) {
			applicationTemplate.addDefinition(element);
		}
		
		return applicationTemplate;
	}

	protected abstract CompositeTemplate basicCreateTemplate(
			String id, 
			CompositeDefinition compositeDefinition, 
			NamesMapping mapping);
}
