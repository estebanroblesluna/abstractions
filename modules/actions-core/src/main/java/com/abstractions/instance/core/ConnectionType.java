package com.abstractions.instance.core;

public enum ConnectionType {

	NEXT_IN_CHAIN_CONNECTION, 
	ALL_CONNECTION, 
	CHOICE_CONNECTION,
	WIRE_TAP_CONNECTION,
	CHAIN_CONNECTION;

	public String getElementName() {
		return this.toString();
	}
}
