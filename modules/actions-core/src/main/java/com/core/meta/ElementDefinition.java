package com.core.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ElementDefinition {

	private String name;
	private String displayName;
	private String icon;
	private List<PropertyDefinition> properties;
	private String implementation;
	private boolean isScript;

	protected ElementDefinition(String name) {
		this.name = name;
		this.properties = new ArrayList<PropertyDefinition>();
		this.isScript = false;
	}
	
	public abstract Object accept(ElementDefinitionVisitor visitor);
	
	public void addProperty(PropertyDefinition property) {
		this.properties.add(property);
	}

	public List<PropertyDefinition> getProperties() {
		return Collections.unmodifiableList(this.properties);
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

	public abstract ElementDefinitionType getType();
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setClassName(String aClassName) {
		this.implementation = aClassName;
		this.isScript = false;
	}

	public String getImplementation() {
		return implementation;
	}

	public boolean isScript() {
		return isScript;
	}
}
