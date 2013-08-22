package com.core.impl;

public enum ConnectionType {

	NEXT_IN_CHAIN, 
	ALL, 
	CHOICE,
	WIRE_TAP;

	public String getElementName() {
		return this + "_CONNECTION";
	}
}
