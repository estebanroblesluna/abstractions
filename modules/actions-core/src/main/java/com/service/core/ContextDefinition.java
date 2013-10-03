package com.service.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.model.ConnectionDefinition;
import com.abstractions.model.ElementDefinition;
import com.core.api.Context;
import com.core.api.Identificable;
import com.core.api.IdentificableMutable;
import com.core.api.Message;
import com.core.api.Processor;
import com.core.api.Startable;
import com.core.api.Terminable;
import com.core.impl.ConnectionType;
import com.core.impl.ContextImpl;
import com.core.interpreter.Interpreter;
import com.core.messagesource.MessageSource;
import com.core.messagesource.MessageSourceListener;
import com.core.utils.IdGenerator;

public class ContextDefinition implements Identificable, MessageSourceListener, Terminable, Startable {

	private static Log log = LogFactory.getLog(ContextDefinition.class);

	private String id;
	private int version;
	private volatile Context context;
	private volatile Map<String, ObjectDefinition> definitions;
	private volatile Map<ObjectDefinition, ExecutorService> executorServices;
	private final NamesMapping mapping;

	public ContextDefinition(NamesMapping mapping) {
		this.id = IdGenerator.getNewId();
		this.version = 1;
		this.definitions = new ConcurrentHashMap<String, ObjectDefinition>();
		this.executorServices = new ConcurrentHashMap<ObjectDefinition, ExecutorService>();
		this.mapping = mapping;
	}
	
	public ContextDefinition(String id, NamesMapping mapping) {
		this(mapping);
		this.id = id;
	}
	
	public void sync() throws ServiceException {
		//if the context is not instantiated then instantiate and initialize
		if (this.context == null) {
			this.instantiate();
		} 
		
		this.initialize();
	}
	
	public Context instantiate() throws ServiceException {
		this.context = new ContextImpl();
		return this.context;
	}
	
	public void initialize() 
	{
		synchronized (this.definitions) {
			//create all objects
			for (ObjectDefinition definition : this.definitions.values())
			{
				try {
					if (!definition.isInstantiated()) {
						Object object = definition.instantiate(this.context, this.mapping);
						if (object instanceof IdentificableMutable) {
							((IdentificableMutable) object).setId(definition.getId());
						}
						if (object instanceof MessageSource) {
							((MessageSource) object).setMainListener(this);
						}
						this.context.addObject(definition.getId(), object);
					}
				} catch (ServiceException e) {
					log.warn("Error instantiating object", e);
				}
			}
			
			//wire them all together
			for (ObjectDefinition definition : this.definitions.values())
			{
				try {
					definition.initialize(this.context, mapping);
				} catch (ServiceException e) {
					log.warn("Error initializing object", e);
				}
			}		
		}
	}

	public void addDefinition(ObjectDefinition definition) {
		synchronized (this.definitions) {
			this.definitions.put(definition.getId(), definition);
			
			if (this.isConnection(definition)) {
				this.bindConnection(definition);
			}
		}
	}
	
	public void addDefinitions(List<ObjectDefinition> definitions) {
		synchronized (this.definitions) {
			for (ObjectDefinition definition : definitions) {
				this.definitions.put(definition.getId(), definition);
				if (this.isConnection(definition)) {
					this.bindConnection(definition);
				}
			}
		}
	}

	public String getId() {
		return id;
	}

	public ObjectDefinition getDefinition(String elementId) {
		synchronized (this.definitions) {
			return this.definitions.get(elementId);
		}
	}

	public Map<String, ObjectDefinition> getDefinitions() {
		return Collections.unmodifiableMap(this.definitions);
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Context getContext() {
		return context;
	}
	
	public void bindConnection(ObjectDefinition definition) {
		if (this.isConnection(definition)) {
			ObjectDefinition sourceDefinition = this.resolve(definition.getProperty("source"));
			ObjectDefinition targetDefinition = this.resolve(definition.getProperty("target"));
			((ConnectionDefinition) definition.getMeta()).addOutgoingConnection(sourceDefinition, definition);
			((ConnectionDefinition) definition.getMeta()).addIncomingConnection(targetDefinition, definition);
		}
	}

	public String addConnection(String sourceId, String targetId, ConnectionType type) {
		ObjectDefinition sourceDefinition = this.getDefinition(sourceId);
		ObjectDefinition targetDefinition = this.getDefinition(targetId);

		if (sourceDefinition != null && targetDefinition != null) {
			ObjectDefinition connectionDefinition = this.createConnection(sourceDefinition, targetDefinition, type);
			this.addDefinition(connectionDefinition);
			return connectionDefinition.getId();
		} else {
			return null;
		}
	}
	
	public ObjectDefinition createConnection(ObjectDefinition sourceDefinition, ObjectDefinition targetDefinition, ConnectionType type) {
		String elementName = type.getElementName();
		ElementDefinition elementDefinition = this.mapping.getDefinition(elementName);
		if (elementDefinition instanceof ConnectionDefinition) {
			ObjectDefinition definition = ((ConnectionDefinition) elementDefinition).createInstance(
					sourceDefinition,
					targetDefinition);
			return definition;
		} else {
			return null;
		}
	}
	
	public void deleteConnection(ObjectDefinition definition) {
		String urn = definition.getProperty("source");
		ObjectDefinition sourceDefinition = this.resolve(urn);
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
			ObjectDefinition definition = this.definitions.remove(elementId);
			
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
	
	private boolean isConnection(ObjectDefinition definition) {
		return definition.getMeta() instanceof ConnectionDefinition;
	}

	public ObjectDefinition getNextInChainFor(String objectDefinitionId) {
		ObjectDefinition definition = this.getDefinition(objectDefinitionId);
		ObjectDefinition nextInChain = definition.getUniqueConnectionOfType(ConnectionType.NEXT_IN_CHAIN_CONNECTION.getElementName(), this);
		if (nextInChain == null) {
			return null;
		}
		
		//target
		String targetURN = nextInChain.getProperty("target");
		ObjectDefinition target = this.resolve(targetURN);

		return target;
	}

	public ObjectDefinition resolve(String urn) {
		if (StringUtils.isEmpty(urn) || !urn.startsWith("urn:")) {
			return null;
		}
		
		String id = this.resolveId(urn);
		return this.getDefinition(id);
	}
	
	public String resolveId(String urn) {
		if (StringUtils.isEmpty(urn) || !urn.startsWith("urn:")) {
			return null;
		}
		
		String id = urn.substring("urn:".length());
		return id;
	}

	public ObjectDefinition getNextInChainFor(ObjectDefinition objectDefinition) {
		if (objectDefinition == null) {
			return null;
		}
		
		return this.getNextInChainFor(objectDefinition.getId());
	}
	
	public Processor getNextProcessorInChainFor(String objectDefinitionId) {
		ObjectDefinition definition = this.getNextInChainFor(objectDefinitionId);
		return (Processor) ((definition != null) ? definition.getInstance() : null);
	}
	
	public ExecutorService getExecutorServiceFor(ObjectDefinition definition) {
		if (!this.executorServices.containsKey(definition)) {
			this.executorServices.put(definition, Executors.newFixedThreadPool(10));
		}
		
		return this.executorServices.get(definition);
	}

	public Message onMessageReceived(MessageSource messageSource, Message message) {
		ObjectDefinition source = this.getNextInChainFor(messageSource.getId());
		Interpreter interpreter = new Interpreter(this, source);
		com.core.interpreter.Thread mainThread = interpreter.run(message);
		Message response = mainThread.getCurrentMessage();
		return response;
	}

	@Override
	public void terminate() {
		for (ObjectDefinition definition : this.definitions.values()) {
			definition.terminate();
		}
		
		for (ExecutorService service : this.executorServices.values()) {
			service.shutdown();
		}
	}

	@Override
	public void start() {
		for (ObjectDefinition definition : this.definitions.values()) {
			definition.start();
		}
	}

	public NamesMapping getMapping() {
		return mapping;
	}
}
