package com.abstractions.meta;

import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.runtime.interpreter.InterpreterDelegate;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.NamesMapping;

public class ApplicationDefinition extends CompositeDefinition {

	private volatile InterpreterDelegate interpreterDelegate;
	private final NamesMapping mapping;
	
	protected ApplicationDefinition() {
		this.mapping = new NamesMapping();
	}

	public ApplicationDefinition(String name) {
		super(name);
		this.mapping = new NamesMapping();
	}

  // ONLY for DEV environment we may need to use a mapping
  public ApplicationDefinition(String name, NamesMapping mapping) {
    super(name);
    this.mapping = mapping;
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
	protected ApplicationTemplate basicCreateTemplate(String id, CompositeDefinition compositeDefinition, NamesMapping mapping) {
		return new ApplicationTemplate(id, this, mapping);
	}

	public ApplicationTemplate createTemplate(NamesMapping mapping) {
		return (ApplicationTemplate) super.createTemplate(mapping);
	}
	
	public NamesMapping getMapping() {
		return mapping;
	}
}
