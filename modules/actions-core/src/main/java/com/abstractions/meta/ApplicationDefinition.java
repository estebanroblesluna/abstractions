package com.abstractions.meta;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.runtime.interpreter.InterpreterDelegate;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.template.CompositeTemplate;

public class ApplicationDefinition extends CompositeDefinition {

	private static final Log log = LogFactory.getLog(ApplicationDefinition.class);

	private volatile InterpreterDelegate interpreterDelegate;
	private final NamesMapping mapping;
	
	protected ApplicationDefinition() {
		this.mapping = new NamesMapping();
	}

	public ApplicationDefinition(String name) {
		super(name);
		this.mapping = new NamesMapping();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evaluateUsing(Thread thread) {
		//NO Evaluation
	}

	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitApplicationDefinition(this);
	}

	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.APPLICATION;
	}
	
	public void setDefaultInterpreterDelegate(InterpreterDelegate interpreterDelegate) {
		this.interpreterDelegate = interpreterDelegate;
	}

	public InterpreterDelegate getInterpreterDelegate() {
		return interpreterDelegate;
	}

	public void setInterpreterDelegate(InterpreterDelegate interpreterDelegate) {
		this.interpreterDelegate = interpreterDelegate;
	}

	@Override
	protected CompositeTemplate basicCreateTemplate(String id, CompositeDefinition compositeDefinition, NamesMapping mapping) {
		return new ApplicationTemplate(id, this, mapping);
	}

	public NamesMapping getMapping() {
		return mapping;
	}
	
	protected String getApplicationId() {
		return Long.valueOf(this.getId()).toString();
	}
}
