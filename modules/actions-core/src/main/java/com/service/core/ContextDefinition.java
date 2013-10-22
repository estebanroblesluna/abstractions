package com.service.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Context;
import com.abstractions.api.Identificable;
import com.abstractions.api.IdentificableMutable;
import com.abstractions.api.Message;
import com.abstractions.api.Processor;
import com.abstractions.api.Startable;
import com.abstractions.api.Terminable;
import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.instance.core.ContextImpl;
import com.abstractions.instance.messagesource.MessageSource;
import com.abstractions.instance.messagesource.MessageSourceListener;
import com.abstractions.meta.ConnectionDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.runtime.interpreter.Interpreter;
import com.abstractions.runtime.interpreter.InterpreterDelegate;
import com.abstractions.utils.IdGenerator;

public class ContextDefinition implements Identificable, MessageSourceListener, Terminable, Startable {

	private static Log log = LogFactory.getLog(ContextDefinition.class);

	private String id;
	private int version;
	private volatile Context context;
	private volatile Map<String, ObjectClazz> definitions;
	private volatile Map<ObjectClazz, ExecutorService> executorServices;
	private final NamesMapping mapping;
	private volatile InterpreterDelegate defaultInterpreterDelegate;

	public ContextDefinition(NamesMapping mapping) {
		this.id = IdGenerator.getNewId();
		this.version = 1;
		this.definitions = new ConcurrentHashMap<String, ObjectClazz>();
		this.executorServices = new ConcurrentHashMap<ObjectClazz, ExecutorService>();
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
			for (ObjectClazz definition : this.definitions.values())
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
			for (ObjectClazz definition : this.definitions.values())
			{
				try {
					definition.initialize(this.context, mapping);
				} catch (ServiceException e) {
					log.warn("Error initializing object", e);
				}
			}		
		}
	}

	public void addDefinition(ObjectClazz definition) {
		synchronized (this.definitions) {
			this.definitions.put(definition.getId(), definition);
			
			if (this.isConnection(definition)) {
				this.bindConnection(definition);
			}
		}
	}
	
	public void addDefinitions(List<ObjectClazz> definitions) {
		synchronized (this.definitions) {
			for (ObjectClazz definition : definitions) {
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

	public ObjectClazz getDefinition(String elementId) {
		synchronized (this.definitions) {
			return this.definitions.get(elementId);
		}
	}

	public Map<String, ObjectClazz> getDefinitions() {
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
	
	public void bindConnection(ObjectClazz definition) {
		if (this.isConnection(definition)) {
			ObjectClazz sourceDefinition = this.resolve(definition.getProperty("source"));
			ObjectClazz targetDefinition = this.resolve(definition.getProperty("target"));
			((ConnectionDefinition) definition.getMeta()).addOutgoingConnection(sourceDefinition, definition);
			((ConnectionDefinition) definition.getMeta()).addIncomingConnection(targetDefinition, definition);
		}
	}

	public ObjectClazz addConnection(String sourceId, String targetId, ConnectionType type) {
		ObjectClazz sourceDefinition = this.getDefinition(sourceId);
		ObjectClazz targetDefinition = this.getDefinition(targetId);

		return addConnection(sourceDefinition, targetDefinition, type);
	}

	public ObjectClazz addConnection(ObjectClazz sourceDefinition, ObjectClazz targetDefinition, ConnectionType type) {
		if (sourceDefinition != null && targetDefinition != null) {
			ObjectClazz connectionDefinition = this.createConnection(sourceDefinition, targetDefinition, type);
			this.addDefinition(connectionDefinition);
			return connectionDefinition;
		} else {
			return null;
		}
	}
	
	public ObjectClazz createConnection(ObjectClazz sourceDefinition, ObjectClazz targetDefinition, ConnectionType type) {
		String elementName = type.getElementName();
		ElementDefinition elementDefinition = this.mapping.getDefinition(elementName);
		if (elementDefinition instanceof ConnectionDefinition) {
			ObjectClazz definition = ((ConnectionDefinition) elementDefinition).createInstance(
					sourceDefinition,
					targetDefinition);
			return definition;
		} else {
			return null;
		}
	}
	
	public void deleteConnection(ObjectClazz definition) {
		String urn = definition.getProperty("source");
		ObjectClazz sourceDefinition = this.resolve(urn);
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
			ObjectClazz definition = this.definitions.remove(elementId);
			
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
	
	private boolean isConnection(ObjectClazz definition) {
		return definition.getMeta() instanceof ConnectionDefinition;
	}

	public ObjectClazz getNextInChainFor(String objectDefinitionId) {
		ObjectClazz definition = this.getDefinition(objectDefinitionId);
		ObjectClazz nextInChain = definition.getUniqueConnectionOfType(ConnectionType.NEXT_IN_CHAIN_CONNECTION.getElementName(), this);
		if (nextInChain == null) {
			return null;
		}
		
		//target
		String targetURN = nextInChain.getProperty("target");
		ObjectClazz target = this.resolve(targetURN);

		return target;
	}

	public ObjectClazz resolve(String urn) {
		if (StringUtils.isEmpty(urn) || !urn.startsWith("urn:")) {
			return null;
		}
		
		String id = this.resolveId(urn);
		return this.getDefinition(id);
	}
	
	public Map<String, ObjectClazz> resolve(List<String> urns) {
		Map<String, ObjectClazz> definitions = new HashMap<String, ObjectClazz>();
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

	public ObjectClazz getNextInChainFor(ObjectClazz objectDefinition) {
		if (objectDefinition == null) {
			return null;
		}
		
		return this.getNextInChainFor(objectDefinition.getId());
	}
	
	public Processor getNextProcessorInChainFor(String objectDefinitionId) {
		ObjectClazz definition = this.getNextInChainFor(objectDefinitionId);
		return (Processor) ((definition != null) ? definition.getInstance() : null);
	}
	
	public ExecutorService getExecutorServiceFor(ObjectClazz definition) {
		if (!this.executorServices.containsKey(definition)) {
			this.executorServices.put(definition, Executors.newFixedThreadPool(10));
		}
		
		return this.executorServices.get(definition);
	}

	public Message onMessageReceived(MessageSource messageSource, Message message) {
		ObjectClazz source = this.getNextInChainFor(messageSource.getId());
		Interpreter interpreter = new Interpreter(this, source);
		if (this.defaultInterpreterDelegate != null) {
			interpreter.setDelegate(this.defaultInterpreterDelegate);
		}
		com.abstractions.runtime.interpreter.Thread mainThread = interpreter.run(message);
		Message response = mainThread.getCurrentMessage();
		return response;
	}

	@Override
	public void terminate() {
		for (ObjectClazz definition : this.definitions.values()) {
			definition.terminate();
		}
		
		for (ExecutorService service : this.executorServices.values()) {
			service.shutdown();
		}
	}

	@Override
	public void start() {
		for (ObjectClazz definition : this.definitions.values()) {
			definition.start();
		}
	}

	public NamesMapping getMapping() {
		return mapping;
	}

	public InterpreterDelegate getDefaultInterpreterDelegate() {
		return defaultInterpreterDelegate;
	}

	public void setDefaultInterpreterDelegate(InterpreterDelegate defaultInterpreterDelegate) {
		this.defaultInterpreterDelegate = defaultInterpreterDelegate;
	}


}
