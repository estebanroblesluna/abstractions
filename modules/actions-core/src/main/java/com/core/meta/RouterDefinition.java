package com.core.meta;

public class RouterDefinition extends ElementDefinition {

	private String routerEvaluatorImplementation;
	private boolean isRouterEvaluatorScript;

	public RouterDefinition(String name) {
		super(name);
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