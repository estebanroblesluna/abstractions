package com.abstractions.meta;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.generalization.AbstractionEvaluator;
import com.abstractions.generalization.AbstractionTemplate;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.ElementTemplate;

public class AbstractionDefinition extends ElementDefinition {

	private static final Log log = LogFactory.getLog(AbstractionDefinition.class);

	private Map<String, ElementTemplate> definitions;
	private ElementTemplate startingDefinition;
	private transient final AbstractionEvaluator evaluator;
	
	protected AbstractionDefinition() {
		this.evaluator = new AbstractionEvaluator();
	}

	public AbstractionDefinition(String name) {
		super(name);
		this.definitions = new HashMap<String, ElementTemplate>();
		this.evaluator = new AbstractionEvaluator();
	}

	public AbstractionDefinition(String name, ElementTemplate startingDefinition) {
		super(name);
		
		Validate.notNull(startingDefinition);
		this.definitions = new HashMap<String, ElementTemplate>();
		this.evaluator = new AbstractionEvaluator();
		this.setStartingDefinition(startingDefinition);
	}

	public AbstractionDefinition(String name, List<ElementTemplate> definitions, ElementTemplate startingDefinition) {
		this(name, startingDefinition);
		this.addDefinitions(definitions);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evaluateUsing(Thread thread) {
		this.evaluator.evaluate(thread);
	}

	public ElementTemplate getStartingDefinition() {
		return startingDefinition;
	}
	
	public void setStartingDefinition(ElementTemplate startingDefinition) {
		this.addDefinition(startingDefinition);
		this.startingDefinition = startingDefinition;
	}

	@Override
	public Element instantiate(CompositeElement context, NamesMapping mapping, Map<String, String> instanceProperties) throws InstantiationException, IllegalAccessException {
		AbstractionTemplate abstraction = new AbstractionTemplate(this, mapping);
		this.basicSetProperties(abstraction, instanceProperties, context, mapping);

		abstraction.setStartingElement(this.startingDefinition);
		abstraction.addDefinitions(this.getDefinitions().values());
		
		try {
			abstraction.sync();
		} catch (ServiceException e) {
			log.warn("Error starting subcontext", e);
		}
		
		
		return abstraction.getInstance();
	}
	
	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitAbstractionDefinition(this);
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
