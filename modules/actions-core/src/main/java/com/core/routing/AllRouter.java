package com.core.routing;

public class AllRouter extends AbstractRouter {
	
	@Override
	public Object accept(RouterVisitor visitor) {
		return visitor.visitAllRouter(this);
	}
}
