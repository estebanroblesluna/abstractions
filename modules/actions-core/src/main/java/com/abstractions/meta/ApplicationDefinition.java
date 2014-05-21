package com.abstractions.meta;

import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.runtime.interpreter.InterpreterDelegate;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.PropertiesLoader;

public class ApplicationDefinition extends CompositeDefinition {

	private volatile InterpreterDelegate interpreterDelegate;
	private final NamesMapping mapping;
	private PropertiesLoader propertiesLoader;
	
	protected ApplicationDefinition() {
		this.mapping = new NamesMapping();
	}

	public ApplicationDefinition(String name, PropertiesLoader propertiesLoader) {
		super(name);
		this.mapping = new NamesMapping();
		this.propertiesLoader = propertiesLoader;
	}

  // ONLY for DEV environment we may need to use a mapping
  public ApplicationDefinition(String name, NamesMapping mapping, PropertiesLoader propertiesLoader) {
    super(name);
    this.mapping = mapping;
    this.propertiesLoader = propertiesLoader;
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
		return new ApplicationTemplate(id, this, mapping, this.propertiesLoader);
	}

	public ApplicationTemplate createTemplate(NamesMapping mapping) {
		return (ApplicationTemplate) super.createTemplate(mapping);
	}
	
	public NamesMapping getMapping() {
		return mapping;
	}
}
