package com.abstractions.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.common.Icon;
import com.abstractions.expression.ScriptingLanguage;
import com.abstractions.instance.common.ScriptingProcessor;
import com.abstractions.model.PropertyDefinition;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.BeanUtils;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.ElementTemplate;


public abstract class ElementDefinition {

	private static Log log = LogFactory.getLog(ElementDefinition.class);
	
	protected long id;
	private String name;
	private String displayName;
	private Icon icon;
	private List<PropertyDefinition> properties;
	private String implementation;
	private boolean isScript;

	protected ElementDefinition() { }

	protected ElementDefinition(String name) {
		this.name = name;
		this.properties = new ArrayList<PropertyDefinition>();
		this.isScript = false;
	}

	public abstract Object accept(ElementDefinitionVisitor visitor);

	public abstract void evaluateUsing(Thread thread);

	public void addProperty(PropertyDefinition property) {
		this.properties.add(property);
	}

	public List<PropertyDefinition> getProperties() {
		return Collections.unmodifiableList(this.properties);
	}

	public String getName() {
		return name;
	}
	
	public String getClassName() {
		return this.name;
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

	public abstract ElementDefinitionType getType();

	public Icon getIcon() {
		return icon;
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	public void setClassName(String aClassName) {
		this.implementation = aClassName;
		this.isScript = false;
	}
	
	public void setScript(boolean isScript) {
		this.isScript = isScript;
	}
	
	public void setScriptText(String aScript) {
		this.implementation = aScript;
		this.isScript = StringUtils.isNotEmpty(aScript);
	}

	public String getImplementation() {
		return implementation;
	}

	public boolean isScript() {
		return isScript;
	}

	public Element instantiate(CompositeElement context, NamesMapping mapping, ElementTemplate template) throws InstantiationException, IllegalAccessException {
		Element object;
		
		if (this.isScript()) {
			object = new ScriptingProcessor(ScriptingLanguage.GROOVY, this.implementation);
		} else {
			Class<?> theClass = mapping.getClassForElement(this.name);
			object = (Element) theClass.newInstance();
		}

		return object;
	}

	public void basicSetProperties(Object object, Map<String, String> properties, CompositeElement context, NamesMapping mapping) {
		if (properties == null || properties.isEmpty()) {
			return;
		}
		
		for (Map.Entry<String, String> entry : properties.entrySet()) {
			String propertyName = entry.getKey();
			String propertyValue = entry.getValue();
			try {
				BeanUtils.setProperty(object, propertyName, propertyValue, context, mapping);
			} catch (ServiceException e) {
				log.warn("Error setting property " + StringUtils.defaultString(propertyName) + " with value " + StringUtils.defaultString(propertyValue));
			}
		}
	}

	public boolean isConnection() {
		return false;
	}

	public void initialize(ElementTemplate template, Map<String, String> properties, CompositeElement container, NamesMapping mapping) {
		Map<String, String> instanceProperties = template.getProperties();

		Map<String, String> initialProperties = mapping.getElementInitialProperties(this.name);
		this.basicSetProperties(template.getInstance(), initialProperties, container, mapping);
		this.basicSetProperties(template.getInstance(), instanceProperties, container, mapping);
	}

	@SuppressWarnings("unchecked")
	public <T> T as(Class<T> theClass) {
		return (T) this;
	}

	public long getId() {
		return this.id;
	}
}
