package com.core.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.core.api.Context;
import com.service.core.NamesMapping;
import com.service.core.ObjectDefinition;

public class FlowDefinition extends ElementDefinition {

	private List<ObjectDefinition> definitions;
	private ObjectDefinition startingDefinition;
	
	public FlowDefinition(String name) {
		super(name);
		this.definitions = new ArrayList<ObjectDefinition>();
	}

	public FlowDefinition(String name, List<ObjectDefinition> definitions) {
		this(name);
		this.addDefinitions(definitions);
	}
	
	@Override
	public Object instantiate(Context context, NamesMapping mapping, Map<String, String> instanceProperties) throws InstantiationException, IllegalAccessException {
		return super.instantiate(context, mapping, instanceProperties);
	}

	@Override
	public void basicSetProperties(Object object, Map<String, String> properties, Context context, NamesMapping mapping) {
		super.basicSetProperties(object, properties, context, mapping);
	}

	public void addDefinition(ObjectDefinition definition) {
		this.definitions.add(definition);
	}

	public void addDefinitions(List<ObjectDefinition> definitions) {
		this.definitions.addAll(definitions);
	}

	@Override
	public Object accept(ElementDefinitionVisitor visitor) {
		return visitor.visitFlowDefinition(this);
	}

	@Override
	public ElementDefinitionType getType() {
		return ElementDefinitionType.FLOW;
	}

	public ObjectDefinition getStartingDefinition() {
		return startingDefinition;
	}

	public void setStartingDefinition(ObjectDefinition startingDefinition) {
		this.startingDefinition = startingDefinition;
	}

	public List<ObjectDefinition> getDefinitions() {
		return Collections.unmodifiableList(this.definitions);
	}
}
