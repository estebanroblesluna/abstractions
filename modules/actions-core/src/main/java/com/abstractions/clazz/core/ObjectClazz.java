package com.abstractions.clazz.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.Validate;

import com.abstractions.api.Context;
import com.abstractions.api.Startable;
import com.abstractions.api.Terminable;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.meta.ConnectionDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.utils.IdGenerator;
import com.service.core.BeanUtils;
import com.service.core.ContextDefinition;
import com.service.core.NamesMapping;
import com.service.core.ServiceException;

public class ObjectClazz implements Startable, Terminable {

	public static final String NAME = "name";
	
	private String id;
	private Map<String, String> properties;
	private final ElementDefinition metaElementDefinition;
	
	private volatile Object object;
	private volatile boolean dirty;
	private volatile boolean hasBreakpoint;
	
	public ObjectClazz(ElementDefinition metaElementDefinition) {
		Validate.notNull(metaElementDefinition);

		this.id = IdGenerator.getNewId();
		this.dirty = false;
		this.hasBreakpoint = false;
		this.properties = new ConcurrentHashMap<String, String>();
		this.metaElementDefinition = metaElementDefinition;
	}

	public ObjectClazz(String id, ElementDefinition metaElementDefinition) {
		Validate.notNull(metaElementDefinition);

		this.id = id;
		this.dirty = false;
		this.properties = new ConcurrentHashMap<String, String>();
		this.metaElementDefinition = metaElementDefinition;
	}

	public synchronized Object instantiate(Context context, NamesMapping mapping) throws ServiceException {
		try {
			this.object = this.metaElementDefinition.instantiate(context, mapping, this.properties);
			return this.object;
		} catch (InstantiationException e) {
			throw new ServiceException("Error creating object");
		} catch (IllegalAccessException e) {
			throw new ServiceException("Error creating object");
		}
	}
	
	public void initialize(Context context, NamesMapping mapping) throws ServiceException {
		if (this.object == null) {
			this.instantiate(context, mapping);
		}

		this.metaElementDefinition.basicSetProperties(this.object, this.properties, context, mapping);
	}
	
	public String getId() {
		return id;
	}

	public Map<String, String> getProperties() {
		return Collections.unmodifiableMap(this.properties);
	}

	public String getElementName() {
		return this.metaElementDefinition.getClassName();
	}

	public void setProperty(String propertyName, String propertyValue) {
		String previousValue = this.properties.put(propertyName, propertyValue);
		this.dirty = (this.object != null && !StringUtils.equals(previousValue, propertyValue));
	}

	public void setProperties(Map<String, String> properties) {
		for (Entry<String, String> entry : properties.entrySet()) {
			this.setProperty(entry.getKey(), entry.getValue());
		}
	}
	
	public boolean isDirty() {
		return dirty;
	}
	
	public boolean isInstantiated() {
		return this.object != null;
	}
	
	public Object getInstance() {
		return this.object;
	}

	public void perform(String actionName, String[] arguments) throws ServiceException {
		if (this.object != null) {
			try {
				MethodUtils.invokeMethod(this.object, actionName, arguments);
			} catch (NoSuchMethodException e) {
				throw new ServiceException("Error invoking the method");
			} catch (IllegalAccessException e) {
				throw new ServiceException("Error invoking the method");
			} catch (InvocationTargetException e) {
				throw new ServiceException("Error invoking the method");
			}
		}
	}

	public boolean hasBreakpoint() {
		return this.hasBreakpoint;
	}

	public void setHasBreakpoint(boolean hasBreakpoint) {
		this.hasBreakpoint = hasBreakpoint;
	}

	public String getProperty(String propertyName) {
		return this.properties.get(propertyName);
	}

	public void removeProperty(String aPropertyName) {
		this.properties.remove(aPropertyName);
		this.dirty = true;
	}

	@Override
	public void terminate() {
		if (this.object != null && (this.object instanceof Terminable)) {
			((Terminable) this.object).terminate();
		}
	}

	@Override
	public void start() {
		if (this.object != null && (this.object instanceof Startable)) {
			((Startable) this.object).start();
		}
	}
	
	public void addConnection(ObjectClazz definition) {
		if (definition.getMeta() instanceof ConnectionDefinition) {
			ConnectionDefinition meta = (ConnectionDefinition)definition.getMeta();
			String type = meta.getName();
			this.addToUrns("__connections", "urn:" + definition.getId());
			this.addToUrns("__connections" + type, "urn:" + definition.getId());
			
			if (type.equals(ConnectionType.NEXT_IN_CHAIN_CONNECTION.getElementName())) {
				this.setProperty("__next_in_chain", "urn:" + definition.getId());
			}
		}
	}
	
	public void removeConnection(ObjectClazz definition) {
		if (definition.getMeta() instanceof ConnectionDefinition) {
			ConnectionDefinition meta = (ConnectionDefinition)definition.getMeta();
			String type = meta.getName();
			this.removeToUrns("__connections", "urn:" + definition.getId());
			this.removeToUrns("__connections" + type, "urn:" + definition.getId());

			if (type.equals(ConnectionType.NEXT_IN_CHAIN_CONNECTION.getElementName())) {
				this.properties.remove("__next_in_chain");
			}
		}
	}
	
	public void addIncomingConnection(ObjectClazz definition) {
		if (definition.getMeta() instanceof ConnectionDefinition) {
			this.addToUrns("__incoming_connections", "urn:" + definition.getId());
		}
	}
	
	public List<String> getIncomingConnections() {
		return BeanUtils.getUrnsFromList(this.getProperty("__incoming_connections"));
	}
	
	public List<String> getOutgoingConnections() {
		return BeanUtils.getUrnsFromList(this.getProperty("__connections"));
	}

	public ObjectClazz getUniqueConnectionOfType(String type, ContextDefinition context) {
		if (type.equals(ConnectionType.NEXT_IN_CHAIN_CONNECTION.getElementName())) {
			String urn = this.properties.get("__next_in_chain");
			if (urn != null) {
				return context.resolve(urn);
			}
		}

		List<String> urns = BeanUtils.getUrnsFromList(this.getProperty("__connections" + type));

		if (urns == null || urns.isEmpty()) {
			return null;
		} else {
			String urn = urns.get(0);
			ObjectClazz object = context.resolve(urn);
			return object;
		}
	}
	
	public List<ObjectClazz> getAllConnectionsOfType(String type, ContextDefinition context) {
		List<String> urns = BeanUtils.getUrnsFromList(this.getProperty("__connections" + type));
		List<ObjectClazz> definitions = new ArrayList<ObjectClazz>();
		
		for (String urn : urns) {
			ObjectClazz object = context.resolve(urn);
			definitions.add(object);
		}
		
		return definitions;
	}
	
	private void addToUrns(String propertyName, String newUrn) {
		String newValue = null;
		
		if (this.getProperty(propertyName) == null) {
			newValue = "list(" + newUrn + ")";
			this.setProperty(propertyName, newValue);		
		} else {
			List<String> oldValues = BeanUtils.getUrnsFromList(this.getProperty(propertyName));
			if (!oldValues.contains(newUrn)) {
				newValue = "list(" + StringUtils.join(oldValues, ',') + "," + newUrn +")";
				this.setProperty(propertyName, newValue);		
			}
		}
		
	}
	
	private void removeToUrns(String propertyName, String urn) {
		List<String> oldValues = new ArrayList<String>(BeanUtils.getUrnsFromList(this.getProperty(propertyName)));
		oldValues.remove(urn);
		
		String newUrns = StringUtils.join(oldValues, ',');
		this.setProperty(propertyName, newUrns);
	}

	public ElementDefinition getMeta() {
		return this.metaElementDefinition;
	}

	/**
	 * Overrides the current instance
	 * USE it with caution as changing the properties may not reflect those changes.
	 * 
	 * @param newInstance
	 */
	public void overrideObject(Object newInstance) {
		this.object = newInstance;
	}
}
