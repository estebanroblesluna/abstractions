package com.core.impl;

public enum ConnectionType {

	NEXT_IN_CHAIN_CONNECTION, 
	ALL_CONNECTION, 
	CHOICE_CONNECTION,
	WIRE_TAP_CONNECTION;

	public String getElementName() {
		return this.toString();
	}
}
