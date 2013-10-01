package com.core.routing;

public class ChainRouter extends AbstractRouter {

	@Override
	public Object accept(RouterVisitor visitor) {
		return visitor.visitChainRouter(this);
	}
}
