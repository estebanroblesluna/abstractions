package com.abstractions.meta;

import com.abstractions.runtime.interpreter.Thread;


public class RouterDefinition extends ElementDefinition {

	private String routerEvaluatorImplementation;
	private boolean isRouterEvaluatorScript;

	protected RouterDefinition() { }

	public RouterDefinition(String name) {
		super(name);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void evaluateUsing(Thread thread) {
		thread.getEvaluator(this.getName()).evaluate(thread);
	}
	
	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.ROUTER;
	}

	public String getRouterEvaluatorImplementation() {
		return routerEvaluatorImplementation;
	}

	public void setRouterEvaluatorImplementation(String routerEvaluatorImplementation) {
		this.routerEvaluatorImplementation = routerEvaluatorImplementation;
	}

	public boolean isRouterEvaluatorScript() {
		return isRouterEvaluatorScript;
	}

	public void setRouterEvaluatorScript(boolean isRouterEvaluatorScript) {
		this.isRouterEvaluatorScript = isRouterEvaluatorScript;
	}
	
	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitRouterDefinition(this);
	}
}
