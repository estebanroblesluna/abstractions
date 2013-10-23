package com.abstractions.meta;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class ApplicationDefinition extends ElementDefinition {

	private static final Log log = LogFactory.getLog(ApplicationDefinition.class);

	private Map<String, ElementTemplate> definitions;
	
	protected ApplicationDefinition() { }

	public ApplicationDefinition(String name) {
		super(name);
		this.definitions = new HashMap<String, ElementTemplate>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evaluateUsing(Thread thread) {
		//NO Evaluation
	}

	@Override
	public Element instantiate(CompositeElement context, NamesMapping mapping, Map<String, String> instanceProperties) throws InstantiationException, IllegalAccessException {
		CompositeTemplate composite = new CompositeTemplate(this, mapping);
		this.basicSetProperties(composite, instanceProperties, context, mapping);

		composite.addDefinitions(this.getDefinitions().values());
		
		try {
			composite.sync();
		} catch (ServiceException e) {
			log.warn("Error starting subcontext", e);
		}
		
		return composite.getInstance();
	}
	
	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitApplicationDefinition(this);
	}

	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.ABSTRACTION;
	}
	
	public String getClassName() {
		return "ABSTRACTION";
	}
	
	public void addDefinition(ElementTemplate definition) {
		this.definitions.put(definition.getId(), definition);
	}

	public void addDefinitions(List<ElementTemplate> definitions) {
		for (ElementTemplate definition : definitions) {
			this.addDefinition(definition);
		}
	}

	public Map<String, ElementTemplate> getDefinitions() {
		return Collections.unmodifiableMap(this.definitions);
	}
}
