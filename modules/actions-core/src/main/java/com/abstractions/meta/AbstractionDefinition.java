package com.abstractions.meta;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.generalization.AbstractionEvaluator;
import com.abstractions.generalization.AbstractionTemplate;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class AbstractionDefinition extends CompositeDefinition {

	private static final Log log = LogFactory.getLog(AbstractionDefinition.class);

	private ElementTemplate startingDefinition;
	private transient final AbstractionEvaluator evaluator;
	private transient Map<ElementTemplate, AbstractionTemplate> elementToAbstractionMapping;
	
	protected AbstractionDefinition() {
		this.evaluator = new AbstractionEvaluator();
		this.elementToAbstractionMapping = new ConcurrentHashMap<ElementTemplate, AbstractionTemplate>();
	}

	public AbstractionDefinition(String name) {
		super(name);
		this.evaluator = new AbstractionEvaluator();
		this.elementToAbstractionMapping = new ConcurrentHashMap<ElementTemplate, AbstractionTemplate>();
	}

	public AbstractionDefinition(String name, ElementTemplate startingDefinition) {
		super(name);
		
		Validate.notNull(startingDefinition);
		this.evaluator = new AbstractionEvaluator();
		this.elementToAbstractionMapping = new ConcurrentHashMap<ElementTemplate, AbstractionTemplate>();
		this.setStartingDefinition(startingDefinition);
	}

	public AbstractionDefinition(String name, List<ElementTemplate> definitions, ElementTemplate startingDefinition) {
		this(name, startingDefinition);
		this.addDefinitions(definitions);
	}
	
	@Override
	public Element instantiate(CompositeElement container, NamesMapping mapping, ElementTemplate template) throws InstantiationException, IllegalAccessException {
		AbstractionTemplate abstraction = (AbstractionTemplate) this.createTemplate(mapping);
		abstraction.setStartingElement(this.startingDefinition);
		
		this.elementToAbstractionMapping.put(template, abstraction);
		
		return super.instantiate(container, mapping, abstraction);
	}
	
	public void initialize(ElementTemplate template, Map<String, String> properties, CompositeElement container, NamesMapping mapping) {
		AbstractionTemplate abstraction = this.elementToAbstractionMapping.get(template);
		super.initialize(abstraction, properties, container, mapping);
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
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitAbstractionDefinition(this);
	}

	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.ABSTRACTION;
	}
	
	@Override
	protected CompositeTemplate basicCreateTemplate(String id, CompositeDefinition compositeDefinition, NamesMapping mapping) {
		return new AbstractionTemplate(id, this, mapping);
	}
}
