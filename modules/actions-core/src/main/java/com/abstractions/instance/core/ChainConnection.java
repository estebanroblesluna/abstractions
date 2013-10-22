package com.abstractions.instance.core;

import com.abstractions.api.Connection;
import com.abstractions.api.Element;
import com.abstractions.instance.routing.ChainRouter;

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
