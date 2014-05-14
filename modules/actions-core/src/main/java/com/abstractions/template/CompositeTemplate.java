package com.abstractions.template;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.api.Processor;
import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.instance.messagesource.MessageSource;
import com.abstractions.meta.ConnectionDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.service.core.NamesMapping;

public class CompositeTemplate extends ElementTemplate {

	private static Log log = LogFactory.getLog(CompositeTemplate.class);

	private volatile Map<String, ElementTemplate> definitions;
	private volatile int version;
	
	private volatile transient Map<ElementTemplate, ExecutorService> executorServices;
	private final transient NamesMapping mapping;

	public CompositeTemplate(ElementDefinition metaElementDefinition, NamesMapping mapping) {
		super(metaElementDefinition);
		
		Validate.notNull(mapping);
		this.definitions = new ConcurrentHashMap<String, ElementTemplate>();
		this.executorServices = new ConcurrentHashMap<ElementTemplate, ExecutorService>();
		this.mapping = mapping;
	}

	public CompositeTemplate(String id, ElementDefinition metaElementDefinition, NamesMapping mapping) {
		super(id, metaElementDefinition);
		
		Validate.notNull(mapping);
		this.definitions = new ConcurrentHashMap<String, ElementTemplate>();
		this.executorServices = new ConcurrentHashMap<ElementTemplate, ExecutorService>();
		this.mapping = mapping;
	}

	public void afterScan(Element object, ElementTemplate definition, ApplicationTemplate appTemplate) {
    if (object instanceof MessageSource) {
      ((MessageSource) object).setMainListener(appTemplate);
    }
	}

	public void afterInstantiation(Element object, ElementTemplate definition, ApplicationTemplate appTemplate) {
    if (object instanceof MessageSource) {
      ((MessageSource) object).setMainListener(appTemplate);
    }
	}

	public void addDefinition(ElementTemplate definition) {
		synchronized (this.definitions) {
			this.definitions.put(definition.getId(), definition);
			
			if (this.isConnection(definition)) {
				this.bindConnection(definition);
			}
		}
	}
	
	public void addDefinitions(Collection<ElementTemplate> definitions) {
		synchronized (this.definitions) {
			List<ElementTemplate> connections = new ArrayList<ElementTemplate>();

			for (ElementTemplate definition : definitions) {
				this.definitions.put(definition.getId(), definition);
				if (this.isConnection(definition)) {
					connections.add(definition);
				}
			}
			
			for (ElementTemplate connection : connections) {
				this.bindConnection(connection);
			}
		}
	}

	public ElementTemplate getDefinition(String elementId) {
		synchronized (this.definitions) {
		  ElementTemplate template = this.definitions.get(elementId);
		  
		  if (template == null) {
		    for (ElementTemplate eleTemplate : this.definitions.values()) {
		      if (eleTemplate instanceof CompositeTemplate) {
		        return ((CompositeTemplate) eleTemplate).getDefinition(elementId);
		      }
		    }
		    
		    return null;
		  } else {
		    return template;
		  }
		}
	}

	public Map<String, ElementTemplate> getDefinitions() {
		return Collections.unmodifiableMap(this.definitions);
	}

	public CompositeElement getCompositeElement() {
		return (CompositeElement) this.object;
	}
	
	public void bindConnection(ElementTemplate definition) {
		if (this.isConnection(definition)) {
			ElementTemplate sourceDefinition = this.resolve(definition.getProperty("source"));
			ElementTemplate targetDefinition = this.resolve(definition.getProperty("target"));
			((ConnectionDefinition) definition.getMeta()).addOutgoingConnection(sourceDefinition, definition);
			((ConnectionDefinition) definition.getMeta()).addIncomingConnection(targetDefinition, definition);
		}
	}

	public ElementTemplate addConnection(String sourceId, String targetId, ConnectionType type) {
		ElementTemplate sourceDefinition = this.getDefinition(sourceId);
		ElementTemplate targetDefinition = this.getDefinition(targetId);

		return addConnection(sourceDefinition, targetDefinition, type);
	}

	public ElementTemplate addConnection(ElementTemplate sourceDefinition, ElementTemplate targetDefinition, ConnectionType type) {
		if (sourceDefinition != null && targetDefinition != null) {
			ElementTemplate connectionDefinition = this.createConnection(sourceDefinition, targetDefinition, type);
			this.addDefinition(connectionDefinition);
			return connectionDefinition;
		} else {
			return null;
		}
	}
	
	public ElementTemplate createConnection(ElementTemplate sourceDefinition, ElementTemplate targetDefinition, ConnectionType type) {
		String elementName = type.getElementName();
		ElementDefinition elementDefinition = this.mapping.getDefinition(elementName);
		if (elementDefinition.isConnection()) {
			ElementTemplate definition = elementDefinition.as(ConnectionDefinition.class).createInstance(
					sourceDefinition,
					targetDefinition);
			return definition;
		} else {
			return null;
		}
	}
	
	public void deleteConnection(ElementTemplate definition) {
		String urn = definition.getProperty("source");
		ElementTemplate sourceDefinition = this.resolve(urn);
		if (sourceDefinition != null) {
			sourceDefinition.removeConnection(definition);
		}
	}
	
	public List<String> deleteDefinition(String elementId) {
		List<String> deletedEntities = new ArrayList<String>();
		if (elementId == null) {
			return deletedEntities;
		}
		
		synchronized (this.definitions) {
			ElementTemplate definition = this.definitions.remove(elementId);
			
			if (definition != null) {
				if (this.isConnection(definition)) {
					this.deleteConnection(definition);
				} else {
					List<String> urns = definition.getIncomingConnections();
					for (String urn : urns) {
						String id = this.resolveId(urn);
						this.deleteDefinition(id);
						deletedEntities.add(id);
					}

					urns = definition.getOutgoingConnections();
					for (String urn : urns) {
						String id = this.resolveId(urn);
						this.deleteDefinition(id);
						deletedEntities.add(id);
					}
				}

				deletedEntities.add(definition.getId());
				definition.terminate();
			}
		}
		
		return deletedEntities;
	}
	
	private boolean isConnection(ElementTemplate definition) {
		return definition.getMeta() instanceof ConnectionDefinition;
	}

	public ElementTemplate getNextInChainFor(String objectDefinitionId) {
		ElementTemplate definition = this.getDefinition(objectDefinitionId);
		ElementTemplate nextInChain = definition.getUniqueConnectionOfType(ConnectionType.NEXT_IN_CHAIN_CONNECTION.getElementName(), this);
		if (nextInChain == null) {
			return null;
		}
		
		//target
		String targetURN = nextInChain.getProperty("target");
		ElementTemplate target = this.resolve(targetURN);

		return target;
	}

	public ElementTemplate resolve(String urn) {
		if (StringUtils.isEmpty(urn) || !urn.startsWith("urn:")) {
			return null;
		}
		
		String id = this.resolveId(urn);
		return this.getDefinition(id);
	}
	
	public Map<String, ElementTemplate> resolve(List<String> urns) {
		Map<String, ElementTemplate> definitions = new HashMap<String, ElementTemplate>();
		for (String urn : urns) {
			definitions.put(urn, this.resolve(urn));
		}
		return definitions;
	}
	
	public String resolveId(String urn) {
		if (StringUtils.isEmpty(urn) || !urn.startsWith("urn:")) {
			return null;
		}
		
		String id = urn.substring("urn:".length());
		return id;
	}

	public ElementTemplate getNextInChainFor(ElementTemplate objectDefinition) {
		if (objectDefinition == null) {
			return null;
		}
		
		return this.getNextInChainFor(objectDefinition.getId());
	}
	
	public Processor getNextProcessorInChainFor(String objectDefinitionId) {
		ElementTemplate definition = this.getNextInChainFor(objectDefinitionId);
		return (Processor) ((definition != null) ? definition.getInstance() : null);
	}
	
	public ExecutorService getExecutorServiceFor(ElementTemplate definition) {
		if (!this.executorServices.containsKey(definition)) {
			synchronized (this.executorServices) {
				if (!this.executorServices.containsKey(definition)) {
					this.executorServices.put(definition, Executors.newFixedThreadPool(80));
				}				
			}
		}
		
		return this.executorServices.get(definition);
	}

	@Override
	public void terminate() {
		for (ElementTemplate definition : this.definitions.values()) {
			definition.terminate();
		}
		
		for (ExecutorService service : this.executorServices.values()) {
			service.shutdown();
		}
	}

	@Override
	public void start() {
		for (ElementTemplate definition : this.definitions.values()) {
			definition.start();
		}
	}

	public NamesMapping getMapping() {
		return mapping;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public CompositeElement getInstance() {
		return (CompositeElement) super.getInstance();
	}
}