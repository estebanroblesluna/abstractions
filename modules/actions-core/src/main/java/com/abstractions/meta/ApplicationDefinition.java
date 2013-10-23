package com.abstractions.meta;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.api.Message;
import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.instance.messagesource.MessageSource;
import com.abstractions.instance.messagesource.MessageSourceListener;
import com.abstractions.runtime.interpreter.Interpreter;
import com.abstractions.runtime.interpreter.InterpreterDelegate;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class ApplicationDefinition extends ElementDefinition implements MessageSourceListener {

	private static final Log log = LogFactory.getLog(ApplicationDefinition.class);

	private Map<String, ElementTemplate> definitions;
	private volatile InterpreterDelegate interpreterDelegate;
	private volatile CompositeTemplate composite;
	
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

	public CompositeTemplate primInstantiate(CompositeElement context, NamesMapping mapping, Map<String, String> instanceProperties) throws InstantiationException, IllegalAccessException {
		this.composite = new ApplicationTemplate(this, mapping);
		this.basicSetProperties(composite, instanceProperties, context, mapping);

		this.composite.addDefinitions(this.getDefinitions().values());
		
		try {
			this.composite.sync();
		} catch (ServiceException e) {
			log.warn("Error starting subcontext", e);
		}
		
		return this.composite;
	}
	
	@Override
	public Element instantiate(CompositeElement context, NamesMapping mapping, Map<String, String> instanceProperties) throws InstantiationException, IllegalAccessException {
		return this.primInstantiate(context, mapping, instanceProperties).getInstance();
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

	public void setDefaultInterpreterDelegate(InterpreterDelegate interpreterDelegate) {
		this.interpreterDelegate = interpreterDelegate;
	}

	@Override
	public Message onMessageReceived(MessageSource messageSource, Message message) {
		ElementTemplate nextInChain = this.composite.getNextInChainFor(messageSource.getId());
		Interpreter interpreter = new Interpreter(this.composite, nextInChain);
		
		if (this.interpreterDelegate != null) {
			interpreter.setDelegate(this.interpreterDelegate);
		}

		Thread root = interpreter.run(message);
		return root.getCurrentMessage();
	}
}
