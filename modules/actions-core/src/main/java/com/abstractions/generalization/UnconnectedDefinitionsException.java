package com.abstractions.generalization;

import java.util.Set;

public class UnconnectedDefinitionsException extends Exception {

	private static final long serialVersionUID = 1L;

	private Set<String> urns;
	
	public UnconnectedDefinitionsException(Set<String> urns) {
		this.urns = urns;
	}

	public Set<String> getUrns() {
		return urns;
	}
}
