package com.core.routing;

public class ChoiceRouter extends AbstractRouter {

	@Override
	public Object accept(RouterVisitor visitor) {
		return visitor.visitChoiceRouter(this);
	}
}
