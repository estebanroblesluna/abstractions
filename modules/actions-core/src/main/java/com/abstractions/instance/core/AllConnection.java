package com.abstractions.instance.core;

import com.abstractions.api.Connection;
import com.abstractions.api.Element;
import com.abstractions.api.Expression;
import com.abstractions.instance.routing.AllRouter;

public class AllConnection extends Connection {

	private Expression targetExpression;
	
	public AllConnection() {
		super();
	}

	public AllConnection(Element source, Element target) {
		super(source, target);
	}
	
	@Override
	public void setSource(Element source) {
		if (this.source != null && this.source instanceof AllRouter) {
			AllRouter choice = (AllRouter) this.source;
			choice.removeConnection(this);
		}

		super.setSource(source);

		if (this.source != null && this.source instanceof AllRouter) {
			AllRouter choice = (AllRouter) this.source;
			choice.addConnection(this);
		}
	}

	public Expression getTargetExpression() {
		return targetExpression;
	}

	public void setTargetExpression(Expression targetExpression) {
		this.targetExpression = targetExpression;
	}
}
