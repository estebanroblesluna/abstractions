package com.core.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Library {

	private String name;
	private String displayName;

	private List<ElementDefinition> definitions;

	public Library(String name) {
		this.name = name;
		this.definitions = new ArrayList<ElementDefinition>();
	}

	public void addDefinition(ElementDefinition definition) {
		this.definitions.add(definition);
	}
	
	public List<ElementDefinition> getDefinitions() {
		return Collections.unmodifiableList(this.definitions);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
