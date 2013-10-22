package com.abstractions.instance.core;

import com.abstractions.api.Connection;
import com.abstractions.api.Element;
import com.abstractions.api.Expression;
import com.abstractions.instance.routing.ChoiceRouter;


public class ChoiceConnection extends Connection {

	private Expression expression;

	@Override
	public void setSource(Element source) {
		if (this.source != null && this.source instanceof ChoiceRouter) {
			ChoiceRouter choice = (ChoiceRouter) this.source;
			choice.removeConnection(this);
		}

		super.setSource(source);

		if (this.source != null && this.source instanceof ChoiceRouter) {
			ChoiceRouter choice = (ChoiceRouter) this.source;
			choice.addConnection(this);
		}
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
}
