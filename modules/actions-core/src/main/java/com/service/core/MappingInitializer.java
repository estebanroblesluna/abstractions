package com.service.core;

import org.apache.commons.lang.ClassUtils;

import com.core.interpreter.RouterEvaluator;
import com.core.meta.ConnectionDefinition;
import com.core.meta.ElementDefinition;
import com.core.meta.ElementDefinitionVisitor;
import com.core.meta.Library;
import com.core.meta.MessageSourceDefinition;
import com.core.meta.Meta;
import com.core.meta.ProcessorDefinition;
import com.core.meta.RouterDefinition;
import com.core.meta.ScriptProcessorDefinition;

public class MappingInitializer {

	private NamesMapping mapping;

	public void initializeMapping() {
		Library common = Meta.getCommonLibrary();
		Library modules = Meta.getModulesLibrary();
		
		this.initializeForLibrary(common);
		this.initializeForLibrary(modules);
	}
	
	private void initializeForLibrary(Library library) {
		for (ElementDefinition element : library.getDefinitions()) {
			try {
				
				element.accept(new ElementDefinitionVisitor() {
					@Override
					public Object visitScriptProcessorDefinition(ScriptProcessorDefinition scriptProcessorDefinition) {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public Object visitRouterDefinition(RouterDefinition routerDefinition) {
						try {
							Class<?> theClass = ClassUtils.getClass(routerDefinition.getRouterEvaluatorImplementation());
							RouterEvaluator evaluator = (RouterEvaluator) theClass.newInstance();
							mapping.addEvaluator(routerDefinition.getName(), evaluator);
						} catch (ClassNotFoundException e) {
						} catch (InstantiationException e) {
						} catch (IllegalAccessException e) {
						}
						return null;
					}
					
					@Override
					public Object visitProcessorDefinition(ProcessorDefinition processorDefinition) {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public Object visitMessageSourceDefinition(MessageSourceDefinition messageSourceDefinition) {
						// TODO Auto-generated method stub
						return null;
					}
					
					@Override
					public Object visitConnectionDefinition(ConnectionDefinition connectionDefinition) {
						// TODO Auto-generated method stub
						return null;
					}
				});
				
				if (!element.isScript()) {
					Class<?> theClass = ClassUtils.getClass(element.getImplementation());
					this.mapping.addMapping(element.getName(), theClass);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public NamesMapping getMapping() {
		return mapping;
	}

	public void setMapping(NamesMapping mapping) {
		this.mapping = mapping;
	}
}
