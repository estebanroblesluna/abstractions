package com.core.routing;

public class WireTapRouter implements Router {

	@Override
	public Object accept(RouterVisitor visitor) {
		return visitor.visitWireTapRouter(this);
	}

}
