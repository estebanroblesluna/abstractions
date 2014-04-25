package com.abstractions.template;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.Validate;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.api.Identificable;
import com.abstractions.api.Startable;
import com.abstractions.api.Terminable;
import com.abstractions.instance.common.ElementInterceptor;
import com.abstractions.instance.common.PerformanceInterceptor;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.meta.ConnectionDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.service.core.BeanUtils;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.utils.IdGenerator;

/**
 * A template represents a definition of the object to be created.
 * 
 * @author Esteban Robles Luna <esteban.roblesluna@gmail.com>
 */
public class ElementTemplate implements Identificable, Startable, Terminable {

	public static final String NAME = "name";
	
	protected String id;
	protected Map<String, String> properties;
	protected ElementDefinition metaElementDefinition;
	
	protected volatile Element object;
	protected volatile boolean dirty;
	protected volatile boolean hasBreakpoint;
	
	protected List<ElementInterceptor> interceptors;
	
	protected ElementTemplate() {
		this.properties = new ConcurrentHashMap<String, String>();
		this.interceptors = new CopyOnWriteArrayList<ElementInterceptor>();
		this.dirty = false;
		this.hasBreakpoint = false;
	}
	
	public ElementTemplate(ElementDefinition metaElementDefinition) {
		this();
		Validate.notNull(metaElementDefinition);

		this.id = IdGenerator.getNewId();
		this.metaElementDefinition = metaElementDefinition;
	}

	public ElementTemplate(String id, ElementDefinition metaElementDefinition) {
		this();
		Validate.notNull(metaElementDefinition);

		this.id = (id == null) ? IdGenerator.getNewId() : id;
		this.properties = new ConcurrentHashMap<String, String>();
		this.metaElementDefinition = metaElementDefinition;
	}

	public void evaluateUsing(com.abstractions.runtime.interpreter.Thread thread) {
		for (ElementInterceptor interceptor : this.interceptors) {
			interceptor.beforeEvaluating(this.getInstance(), thread.getCurrentMessage());
		}
		
		this.getMeta().evaluateUsing(thread);		

		for (ElementInterceptor interceptor : this.interceptors) {
			interceptor.afterEvaluating(this.getInstance(), thread.getCurrentMessage());
		}
	}
	
	public synchronized Element sync(CompositeElement container, NamesMapping mapping) throws ServiceException {
		if (this.object == null) {
			this.instantiate(container, mapping);
		}
		
		this.initialize(container, mapping);
		
		return this.object;
	}
	
	public synchronized Element instantiate(CompositeElement context, NamesMapping mapping) throws ServiceException {
		try {
			this.object = this.metaElementDefinition.instantiate(context, mapping, this);
			return this.object;
		} catch (InstantiationException e) {
			throw new ServiceException("Error creating object");
		} catch (IllegalAccessException e) {
			throw new ServiceException("Error creating object");
		}
	}
	
	public void initialize(CompositeElement container, NamesMapping mapping) throws ServiceException {
		if (this.object == null) {
			this.instantiate(container, mapping);
		}
		this.metaElementDefinition.initialize(this, this.properties, container, mapping);
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
	
	public Element getInstance() {
		return this.object;
	}

	public Object perform(String actionName, String[] arguments) throws ServiceException {
		if (this.object != null) {
			try {
				Object result = MethodUtils.invokeMethod(this.object, actionName, arguments);
				return result;
			} catch (NoSuchMethodException e) {
				throw new ServiceException("Error invoking the method");
			} catch (IllegalAccessException e) {
				throw new ServiceException("Error invoking the method");
			} catch (InvocationTargetException e) {
				throw new ServiceException("Error invoking the method");
			}
		}
		
		return null;
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
	
	public void addConnection(ElementTemplate definition) {
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
	
	public void removeConnection(ElementTemplate definition) {
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
	
	public void addIncomingConnection(ElementTemplate definition) {
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

	public ElementTemplate getUniqueConnectionOfType(String type, CompositeTemplate context) {
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
			ElementTemplate object = context.resolve(urn);
			return object;
		}
	}
	
	public List<ElementTemplate> getAllConnectionsOfType(String type, CompositeTemplate context) {
		List<String> urns = BeanUtils.getUrnsFromList(this.getProperty("__connections" + type));
		List<ElementTemplate> definitions = new ArrayList<ElementTemplate>();
		
		for (String urn : urns) {
			ElementTemplate object = context.resolve(urn);
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
	public void overrideObject(Element newInstance) {
		this.object = newInstance;
	}
	
	public void addInterceptor(ElementInterceptor interceptor) {
		Validate.notNull(interceptor);
		this.interceptors.add(interceptor);
	}
	
	public void removeInterceptor(ElementInterceptor interceptor) {
		Validate.notNull(interceptor);
		this.interceptors.remove(interceptor);
	}

	@SuppressWarnings("unchecked")
	public <T> T getFirstInterceptorOfClass(Class<?> aClass) {
		for (ElementInterceptor interceptor : this.interceptors) {
			if (aClass.isAssignableFrom(interceptor.getClass())) {
				return (T) interceptor;
			}
		}
		return null;
	}
}
