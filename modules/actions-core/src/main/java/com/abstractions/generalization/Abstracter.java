package com.abstractions.generalization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.abstractions.meta.AbstractionDefinition;
import com.abstractions.meta.ElementDefinitionType;
import com.abstractions.model.PropertyDefinition;
import com.abstractions.model.PropertyType;
import com.abstractions.service.core.BeanUtils;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class Abstracter {

	public AbstractionDefinition abstractFrom(String name, CompositeTemplate application, ElementTemplate... definitions) throws UnconnectedDefinitionsException, MultipleEntryPointsException {
		Map<String, ElementTemplate> definitionsMap = new HashMap<String, ElementTemplate>();
		for (ElementTemplate definition : definitions) {
			definitionsMap.put(definition.getId(), definition);
		}

		return this.abstractFrom(name, application, definitionsMap);
	}

	public AbstractionDefinition abstractFrom(String name, CompositeTemplate application, List<String> urns) throws UnconnectedDefinitionsException, MultipleEntryPointsException {
		Map<String, ElementTemplate> definitionsMap = new HashMap<String, ElementTemplate>();
		for (String urn : urns) {
			ElementTemplate template = application.resolve(BeanUtils.asUrn(urn));
			if (template != null) {
				definitionsMap.put(urn, template);
			}
		}
	
		return this.abstractFrom(name, application, definitionsMap);
	}

	public AbstractionDefinition abstractFrom(String name, CompositeTemplate application, Map<String, ElementTemplate> definitionsMap) throws UnconnectedDefinitionsException, MultipleEntryPointsException {
		Set<ElementTemplate> definitions = new HashSet<ElementTemplate>(definitionsMap.values());

		if (this.isConnected(definitions, application)) {
			List<Map.Entry<ElementTemplate, Long>> sortedExternalDefinitions = this.topologicalSortByIncomingExternalConnections(definitions, application);
			if (this.hasOneEntry(sortedExternalDefinitions)) {
				List<Map.Entry<ElementTemplate, Long>> sortedInternalDefinitions = this.topologicalSortByIncomingInternalConnections(definitions, application);
				AbstractionDefinition abstraction = this.createAbstraction(name, sortedInternalDefinitions.get(0).getKey(), definitions);
			
				PropertyDefinition nameProperty = new PropertyDefinition("name");
				nameProperty.setDisplayName("Name");
				nameProperty.setType(PropertyType.STRING);
				nameProperty.setDefaultValue(name);
				
				abstraction.addProperty(nameProperty);

				return abstraction;
			} else {
				throw new MultipleEntryPointsException(definitionsMap.keySet());
			}
		} else {
			throw new UnconnectedDefinitionsException(definitionsMap.keySet());
		}
	}

	/**
	 * Creates the Abstractions from the definition list
	 * @param name 
	 */
	private AbstractionDefinition createAbstraction(String name, ElementTemplate starting, Set<ElementTemplate> definitions) {
		AbstractionDefinition abstraction = new AbstractionDefinition(name, starting);
		
		for (ElementTemplate definition : definitions) {
			abstraction.addDefinition(definition);
		}
		
		return abstraction;
	}

	/**
	 * Verifies that the subgraph has only one node that receives the incoming connections and this will be
	 * the starting node
	 */
	private boolean hasOneEntry(List<Map.Entry<ElementTemplate, Long>> definitions) {
		Entry<ElementTemplate, Long> entry = definitions.get(definitions.size() - 1);
		if (entry.getValue().longValue() == 0) {
			return true;
		} else {
			//verify that the previous one has 0 connections
			Entry<ElementTemplate, Long> beforeLast = definitions.get(definitions.size() - 2);
			return beforeLast.getValue().longValue() == 0;
		}
	}

	/**
	 * Runs a topological sort including incoming connections from external sources to the subgraph
	 */
	private List<Map.Entry<ElementTemplate, Long>> topologicalSortByIncomingExternalConnections(Set<ElementTemplate> definitions, CompositeTemplate application) {
		final Map<ElementTemplate, Long> incomingConnections = new HashMap<ElementTemplate, Long>();
		
		for (ElementTemplate definition : definitions) {
			if (!definition.getMeta().isConnection()) {
				long counter = 0;

				for (String connectionUrn : definition.getIncomingConnections()) {
					ElementTemplate connection = application.resolve(connectionUrn);
					if (connection != null) {
						String sourceUrn = connection.getProperty("source");
						ElementTemplate source = application.resolve(sourceUrn);
						if (!definitions.contains(source)) {
							counter++;
						}
					}				
				}

				incomingConnections.put(definition, counter);
			}
		}
		
		return entriesSortedByValues(incomingConnections);
	}
	
	/**
	 * Runs a topological sort excluding incoming connections from external sources to the subgraph
	 */
	private List<Map.Entry<ElementTemplate, Long>> topologicalSortByIncomingInternalConnections(Set<ElementTemplate> definitions, CompositeTemplate application) {
		final Map<ElementTemplate, Long> incomingConnections = new HashMap<ElementTemplate, Long>();
		
		for (ElementTemplate definition : definitions) {
			if (!definition.getMeta().isConnection()) {
				long counter = 0;

				for (String connectionUrn : definition.getIncomingConnections()) {
					ElementTemplate connection = application.resolve(connectionUrn);
					if (connection != null) {
						String sourceUrn = connection.getProperty("source");
						ElementTemplate source = application.resolve(sourceUrn);
						if (definitions.contains(source)) {
							counter++;
						}
					}				
				}

				incomingConnections.put(definition, counter);
			}
		}
		
		return entriesSortedByValues(incomingConnections);
	}
	
	private <K,V extends Comparable<V>> List<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
		List<Map.Entry<K,V>> sortedEntries = new ArrayList<Map.Entry<K,V>>();
		sortedEntries.addAll(map.entrySet());
		
		Collections.sort(sortedEntries,
	        new Comparator<Map.Entry<K,V>>() {
	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
	                return e1.getValue().compareTo(e2.getValue());
	            }
	        }
	    );

		return sortedEntries;
	}

	/**
	 * Verifies that the subgraph is a connected subgraph
	 */
	private boolean isConnected(Set<ElementTemplate> definitions, CompositeTemplate application) {
		for (ElementTemplate definition : definitions) {
			if (definition.getMeta().getType().equals(ElementDefinitionType.CONNECTION)) {
				String sourceUrn = definition.getProperty("source");
				String targetUrn = definition.getProperty("target");
				
				ElementTemplate source = application.resolve(sourceUrn);
				ElementTemplate target = application.resolve(targetUrn);
				
				if (!definitions.contains(source) || !definitions.contains(target)) {
					return false;
				}
			} else {
				for (String connectionUrn : definition.getOutgoingConnections()) {
					ElementTemplate connection = application.resolve(connectionUrn);
					if (connection != null) {
						String targetUrn = connection.getProperty("target");
						ElementTemplate target = application.resolve(targetUrn);
						if (!definitions.contains(target)) {
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}
}
