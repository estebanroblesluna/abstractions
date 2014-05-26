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
import com.abstractions.generalization.ApplicationTemplate;
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
	public Element instantiate(CompositeElement container, NamesMapping mapping, ElementTemplate template, ApplicationTemplate appTemplate) throws InstantiationException, IllegalAccessException {
		CompositeTemplate compositeTemplate = (CompositeTemplate) template;
		
		CompositeElementImpl compositeElement = null;
		if (!compositeTemplate.isInstantiated()) {
			compositeElement = this.basicCreateInstance(compositeTemplate, container);
			compositeTemplate.overrideObject(compositeElement);
		} else {
			compositeElement = (CompositeElementImpl) compositeTemplate.getInstance();
		}
		
		synchronized (compositeTemplate.getDefinitions()) {
			for (ElementTemplate elementTemplate : compositeTemplate.getDefinitions().values()) {
				try {
					if (!elementTemplate.isInstantiated()) {
						Element element = elementTemplate.instantiate(compositeElement, mapping, appTemplate);
						if (element instanceof IdentificableMutable) {
							((IdentificableMutable) element).setId(elementTemplate.getId());
						}
						compositeTemplate.afterInstantiation(element, elementTemplate, appTemplate);
						compositeElement.addObject(elementTemplate.getId(), element);
					}
					compositeTemplate.afterScan(elementTemplate.getInstance(), elementTemplate, appTemplate);
				} catch (ServiceException e) {
					log.warn("Error instantiating object", e);
				}
			}
		}

		return compositeElement;
	}
	
	protected CompositeElementImpl basicCreateInstance(CompositeTemplate compositeTemplate, CompositeElement container) {
		return new CompositeElementImpl(compositeTemplate, container);
	}

	public void initialize(ElementTemplate template, Map<String, String> properties, CompositeElement container, NamesMapping mapping, ApplicationTemplate appTemplate) {
		super.initialize(template, properties, container, mapping, appTemplate);
		
		CompositeTemplate composite = (CompositeTemplate) template;
		CompositeElement compositeElement = composite.getCompositeElement();
		
		synchronized (composite.getDefinitions()) {
			//wire them all together
			this.initializeTemplates(composite, compositeElement, mapping, composite.getDefinitions().values(), appTemplate);		
		}
	}

	public void initializeTemplates(
			CompositeTemplate templatesContainer, 
			CompositeElement parent, 
			NamesMapping mapping, 
			Collection<ElementTemplate> templates, 
			ApplicationTemplate appTemplate) {
		
		for (ElementTemplate definition : templates) {
			try {
				if (!definition.isInstantiated()) {
					Element element = definition.instantiate(parent, mapping, appTemplate);
					if (element instanceof IdentificableMutable) {
						((IdentificableMutable) element).setId(definition.getId());
					}
					templatesContainer.afterInstantiation(element, definition, appTemplate);
				}
				definition.initialize(parent, mapping, appTemplate);

				templatesContainer.afterScan(definition.getInstance(), definition, appTemplate);
			} catch (ServiceException e) {
				log.warn("Error initializing object", e);
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
