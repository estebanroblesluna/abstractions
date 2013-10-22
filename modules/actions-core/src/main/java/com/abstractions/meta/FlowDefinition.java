package com.abstractions.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Context;
import com.abstractions.clazz.composition.Flow;
import com.abstractions.clazz.core.ObjectClazz;
import com.service.core.ContextDefinition;
import com.service.core.NamesMapping;
import com.service.core.ServiceException;

public class FlowDefinition extends ElementDefinition {

	static final Log log = LogFactory.getLog(FlowDefinition.class);
	
	private List<ObjectClazz> definitions;
	private ObjectClazz startingDefinition;
	
	protected FlowDefinition() { }

	public FlowDefinition(String name) {
		super(name);
		this.definitions = new ArrayList<ObjectClazz>();
	}

	public FlowDefinition(String name, List<ObjectClazz> definitions) {
		this(name);
		this.addDefinitions(definitions);
	}
	
	public String getClassName() {
		return "FLOW";
	}
	
	@Override
	public Object instantiate(Context context, NamesMapping mapping, Map<String, String> instanceProperties) throws InstantiationException, IllegalAccessException {
		Flow flow = new Flow();
		this.basicSetProperties(flow, instanceProperties, context, mapping);

		ContextDefinition subContextDefinition = new ContextDefinition(mapping);
		subContextDefinition.addDefinitions(this.definitions);
		
		try {
			subContextDefinition.sync();
		} catch (ServiceException e) {
			log.warn("Error starting subcontext", e);
		}
		
		flow.setStarting(this.startingDefinition);
		flow.setContext(subContextDefinition);
		
		return flow;
	}

	@Override
	public void basicSetProperties(Object object, Map<String, String> properties, Context context, NamesMapping mapping) {
		super.basicSetProperties(object, properties, context, mapping);
	}

	public void addDefinition(ObjectClazz definition) {
		this.definitions.add(definition);
	}

	public void addDefinitions(List<ObjectClazz> definitions) {
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

	public ObjectClazz getStartingDefinition() {
		return startingDefinition;
	}

	public void setStartingDefinition(ObjectClazz startingDefinition) {
		this.startingDefinition = startingDefinition;
	}

	public List<ObjectClazz> getDefinitions() {
		return Collections.unmodifiableList(this.definitions);
	}
}
