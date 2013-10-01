package com.core.impl;

import com.core.api.Connection;
import com.core.api.Element;
import com.core.routing.ChainRouter;

public class ChainConnection extends Connection {

	@Override
	public void setSource(Element source) {
		if (this.source != null && this.source instanceof ChainRouter) {
			ChainRouter choice = (ChainRouter) this.source;
			choice.removeConnection(this);
		}

		super.setSource(source);

		if (this.source != null && this.source instanceof ChainRouter) {
			ChainRouter choice = (ChainRouter) this.source;
			choice.addConnection(this);
		}
	}
}
