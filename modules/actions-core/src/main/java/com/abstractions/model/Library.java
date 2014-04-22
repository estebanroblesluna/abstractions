package com.abstractions.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;

import com.abstractions.generalization.AbstractionEvaluator;
import com.abstractions.meta.AbstractionDefinition;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.ConnectionDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.meta.ElementDefinitionVisitor;
import com.abstractions.meta.MessageSourceDefinition;
import com.abstractions.meta.ProcessorDefinition;
import com.abstractions.meta.RouterDefinition;
import com.abstractions.runtime.interpreter.Evaluator;
import com.abstractions.service.core.NamesMapping;

public class Library {

	private long id;
	private String name;
	private String displayName;

	private List<ElementDefinition> definitions;
	private Map<String, ElementDefinition> definitionsByName;

	protected Library() {
		this.definitions = new ArrayList<ElementDefinition>();
		this.definitionsByName = new HashMap<String, ElementDefinition>();
	}

	public Library(String name) {
		this();
		this.name = name;
	}

	public void addDefinition(ElementDefinition definition) {
		this.definitions.add(definition);
		this.definitionsByName.put(definition.getName(), definition);
	}
	
	public void addBasicDefinitionForClass(String name, Class<?> aClass) {
		ElementDefinition definition = new ProcessorDefinition(name);
		definition.setClassName(aClass.getCanonicalName());
		
		this.addDefinition(definition);
	}

	public void addBasicDefinitionForClass(String name, String script) {
		ElementDefinition definition = new ProcessorDefinition(name);
		definition.setScriptText(script);
		
		this.addDefinition(definition);
	}

	public List<ElementDefinition> getDefinitions() {
		return Collections.unmodifiableList(this.definitions);
	}
	
	public ElementDefinition getDefinition(String name) {
		return this.definitionsByName.get(name);
	}
	
	public void createMappings(final NamesMapping mapping) {
		for (ElementDefinition element : this.getDefinitions()) {
			element.accept(new ElementDefinitionVisitor() {
				@Override
				public Object visitRouterDefinition(RouterDefinition routerDefinition) {
					try {
						Class<?> theClass = ClassUtils.getClass(routerDefinition.getRouterEvaluatorImplementation());
						Evaluator evaluator = (Evaluator) theClass.newInstance();
						mapping.addEvaluator(routerDefinition.getName(), evaluator);
					} catch (ClassNotFoundException e) {
					} catch (InstantiationException e) {
					} catch (IllegalAccessException e) {
					}
					return null;
				}
				
				@Override
				public Object visitProcessorDefinition(ProcessorDefinition processorDefinition) {
					return null;
				}
				
				@Override
				public Object visitMessageSourceDefinition(MessageSourceDefinition messageSourceDefinition) {
					return null;
				}
				
				@Override
				public Object visitConnectionDefinition(ConnectionDefinition connectionDefinition) {
					return null;
				}

				@Override
				public Object visitAbstractionDefinition(AbstractionDefinition abstractionDefinition) {
					mapping.addEvaluator(abstractionDefinition.getName(), new AbstractionEvaluator());
					return null;
				}

				@Override
				public Object visitApplicationDefinition(ApplicationDefinition applicationDefinition) {
					return null;
				}
			});
			
			mapping.addMapping(element.getName(), element);
		}		
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
        
        public long getId() {
                return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
