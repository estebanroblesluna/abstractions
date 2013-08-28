package com.service.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;

import com.core.api.Context;
import com.core.api.Startable;
import com.core.api.Terminable;
import com.core.impl.ConnectionType;
import com.core.utils.IdGenerator;

public class ObjectDefinition implements Startable, Terminable {

	private String id;
	private String elementName;
	private Map<String, String> properties;
	
	private volatile Object object;
	private volatile boolean dirty;
	private volatile boolean hasBreakpoint;
	
	public ObjectDefinition(String elementName) {
		this.id = IdGenerator.getNewId();
		this.elementName = elementName;
		this.dirty = false;
		this.hasBreakpoint = false;
		this.properties = new ConcurrentHashMap<String, String>();
	}

	public ObjectDefinition(String id, String elementName) {
		this.id = id;
		this.elementName = elementName;
		this.dirty = false;
		this.properties = new ConcurrentHashMap<String, String>();
	}

	public synchronized Object instantiate(Context context, NamesMapping mapping) throws ServiceException {
		try {
			Class<?> theClass = mapping.getClassForElement(this.elementName);
			this.object = theClass.newInstance();
			
			Map<String, String> initialProperties = mapping.getElementInitialProperties(this.elementName);
			if (initialProperties != null) {
				this.basicSetProperties(initialProperties, context, mapping);
			}

			return this.object;
		} catch (InstantiationException e) {
			throw new ServiceException("Error creating object");
		} catch (IllegalAccessException e) {
			throw new ServiceException("Error creating object");
		}
	}
	
	public void initialize(Context context, NamesMapping mapping) throws ServiceException 
	{
		if (this.object == null) {
			this.instantiate(context, mapping);
		}
		
		this.basicSetProperties(this.properties, context, mapping);
	}
	
	private void basicSetProperties(Map<String, String> properties, Context context, NamesMapping mapping) {
		for (Map.Entry<String, String> entry : properties.entrySet())
		{
			String propertyName = entry.getKey();
			String propertyValue = entry.getValue();
			try 
			{
				BeanUtils.setProperty(this.object, propertyName, propertyValue, context, mapping);
			} 
			catch (ServiceException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public Map<String, String> getProperties() {
		return Collections.unmodifiableMap(this.properties);
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
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
	
	public void addConnection(ObjectDefinition definition) {
		if (definition.getElementName().endsWith("_CONNECTION")) {
			String type = definition.getProperty("type");
			this.addToUrns("__connections" + type, "urn:" + definition.getId());
			
			if (type.equals(ConnectionType.NEXT_IN_CHAIN_CONNECTION.getElementName())) {
				this.setProperty("__next_in_chain", "urn:" + definition.getId());
			}
		}
	}
	
	public void removeConnection(ObjectDefinition definition) {
		if (definition.getElementName().endsWith("_CONNECTION")) {
			String type = definition.getProperty("type");
			if (type == null) {
				type = definition.getElementName();
			}
			this.removeToUrns("__connections" + type, "urn:" + definition.getId());

			if (type.equals(ConnectionType.NEXT_IN_CHAIN_CONNECTION.getElementName())) {
				this.properties.remove("__next_in_chain");
			}
		}
	}
	
	public ObjectDefinition getUniqueConnectionOfType(String type, ContextDefinition context) {
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
			ObjectDefinition object = context.resolve(urn);
			return object;
		}
	}
	
	public List<ObjectDefinition> getAllConnectionsOfType(String type, ContextDefinition context) {
		List<String> urns = BeanUtils.getUrnsFromList(this.getProperty("__connections" + type));
		List<ObjectDefinition> definitions = new ArrayList<ObjectDefinition>();
		
		for (String urn : urns) {
			ObjectDefinition object = context.resolve(urn);
			definitions.add(object);
		}
		
		return definitions;
	}
	
	private void addToUrns(String propertyName, String newUrn) {
		String newValue = null;
		
		if (this.getProperty(propertyName) == null) {
			newValue = "list(" + newUrn + ")";
		} else {
			String oldValues = BeanUtils.getUrnsFromListAsString(this.getProperty(propertyName));
			newValue = "list(" + oldValues + "," + newUrn +")";
		}
		
		this.setProperty(propertyName, newValue);		
	}
	
	private void removeToUrns(String propertyName, String urn) {
		List<String> oldValues = BeanUtils.getUrnsFromList(this.getProperty(propertyName));
		oldValues.remove(urn);
		
		String newUrns = StringUtils.join(oldValues, ',');
		this.setProperty(propertyName, newUrns);
	}
}
