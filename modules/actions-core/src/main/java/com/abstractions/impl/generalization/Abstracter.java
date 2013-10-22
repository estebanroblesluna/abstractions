package com.abstractions.impl.generalization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.abstractions.model.ElementDefinitionType;
import com.service.core.ContextDefinition;
import com.service.core.ObjectDefinition;

public class Abstracter {

	public Abstraction abstractFrom(ContextDefinition context, ObjectDefinition... definitions) throws UnconnectedDefinitionsException, MultipleEntryPointsException {
		Map<String, ObjectDefinition> definitionsMap = new HashMap<String, ObjectDefinition>();
		for (ObjectDefinition definition : definitions) {
			definitionsMap.put(definition.getId(), definition);
		}

		return this.abstractFrom(context, definitionsMap);
	}

	public Abstraction abstractFrom(ContextDefinition context, List<String> urns) throws UnconnectedDefinitionsException, MultipleEntryPointsException {
		Map<String, ObjectDefinition> definitionsMap = new HashMap<String, ObjectDefinition>();
		for (String urn : urns) {
			definitionsMap.put(urn, context.resolve(urn));
		}
	
		return this.abstractFrom(context, definitionsMap);
	}

	public Abstraction abstractFrom(ContextDefinition context, Map<String, ObjectDefinition> definitionsMap) throws UnconnectedDefinitionsException, MultipleEntryPointsException {
		Set<ObjectDefinition> definitions = new HashSet<ObjectDefinition>(definitionsMap.values());

		if (this.isConnected(definitions, context)) {
			List<Map.Entry<ObjectDefinition, Long>> sortedDefinitions = this.topologicalSortByIncomingExternalConnections(definitions, context);
			if (this.hasOneEntry(sortedDefinitions)) {
				return this.createAbstraction(sortedDefinitions);
			} else {
				throw new MultipleEntryPointsException(definitionsMap.keySet());
			}
		} else {
			throw new UnconnectedDefinitionsException(definitionsMap.keySet());
		}
	}

	/**
	 * Creates the Abstractions from the definition list
	 */
	private Abstraction createAbstraction(List<Map.Entry<ObjectDefinition, Long>> definitions) {
		Abstraction abstraction = new Abstraction(definitions.get(definitions.size() - 1).getKey());
		
		for (Map.Entry<ObjectDefinition, Long> definition : definitions) {
			abstraction.addDefinition(definition.getKey());
		}
		
		return abstraction;
	}

	/**
	 * Verifies that the subgraph has only one node that receives the incoming connections and this will be
	 * the starting node
	 */
	private boolean hasOneEntry(List<Map.Entry<ObjectDefinition, Long>> definitions) {
		Entry<ObjectDefinition, Long> entry = definitions.get(definitions.size() - 1);
		if (entry.getValue().longValue() == 0) {
			return true;
		} else {
			//verify that the previous one has 0 connections
			Entry<ObjectDefinition, Long> beforeLast = definitions.get(definitions.size() - 2);
			return beforeLast.getValue().longValue() == 0;
		}
	}

	/**
	 * Runs a topological sort excluding incoming connections from external sources to the subgraph
	 */
	private List<Map.Entry<ObjectDefinition, Long>> topologicalSortByIncomingExternalConnections(Set<ObjectDefinition> definitions, ContextDefinition context) {
		final Map<ObjectDefinition, Long> incomingConnections = new HashMap<ObjectDefinition, Long>();
		
		for (ObjectDefinition definition : definitions) {
			long counter = 0;
			
			for (String connectionUrn : definition.getIncomingConnections()) {
				ObjectDefinition connection = context.resolve(connectionUrn);
				if (connection != null) {
					String sourceUrn = connection.getProperty("source");
					ObjectDefinition source = context.resolve(sourceUrn);
					if (!definitions.contains(source)) {
						counter++;
					}
				}				
			}
			
			incomingConnections.put(definition, counter);
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
	private boolean isConnected(Set<ObjectDefinition> definitions, ContextDefinition context) {
		for (ObjectDefinition definition : definitions) {
			if (definition.getMeta().getType().equals(ElementDefinitionType.CONNECTION)) {
				String sourceUrn = definition.getProperty("source");
				String targetUrn = definition.getProperty("target");
				
				ObjectDefinition source = context.resolve(sourceUrn);
				ObjectDefinition target = context.resolve(targetUrn);
				
				if (!definitions.contains(source) || !definitions.contains(target)) {
					return false;
				}
			} else {
				for (String connectionUrn : definition.getOutgoingConnections()) {
					ObjectDefinition connection = context.resolve(connectionUrn);
					if (connection != null) {
						String targetUrn = connection.getProperty("target");
						ObjectDefinition target = context.resolve(targetUrn);
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
