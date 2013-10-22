package com.abstractions.impl.generalization;

import java.util.Set;

public class MultipleEntryPointsException extends Exception {

	private static final long serialVersionUID = 1L;

	private Set<String> urns;
	
	public MultipleEntryPointsException(Set<String> urns) {
		this.urns = urns;
	}

	public Set<String> getUrns() {
		return urns;
	}
}
