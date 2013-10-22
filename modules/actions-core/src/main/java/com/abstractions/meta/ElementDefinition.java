package com.abstractions.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Context;
import com.abstractions.expression.ScriptingLanguage;
import com.abstractions.instance.common.ScriptingProcessor;
import com.abstractions.model.PropertyDefinition;
import com.service.core.BeanUtils;
import com.service.core.NamesMapping;
import com.service.core.ServiceException;

public abstract class ElementDefinition {

	private static Log log = LogFactory.getLog(ElementDefinition.class);
	
	long id;
	private String name;
	private String displayName;
	private String icon;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setClassName(String aClassName) {
		this.implementation = aClassName;
		this.isScript = false;
	}
	
	public void setScript(String aScript) {
		this.implementation = aScript;
		this.isScript = true;
	}

	public String getImplementation() {
		return implementation;
	}

	public boolean isScript() {
		return isScript;
	}

	public Object instantiate(Context context, NamesMapping mapping, Map<String, String> instanceProperties) throws InstantiationException, IllegalAccessException {
		Object object;
		
		if (this.isScript()) {
			object = new ScriptingProcessor(ScriptingLanguage.GROOVY, this.implementation);
		} else {
			Class<?> theClass = mapping.getClassForElement(this.name);
			object = theClass.newInstance();

			Map<String, String> initialProperties = mapping.getElementInitialProperties(this.name);
			this.basicSetProperties(object, initialProperties, context, mapping);
			this.basicSetProperties(object, instanceProperties, context, mapping);
		}

		return object;
	}

	public void basicSetProperties(Object object, Map<String, String> properties, Context context, NamesMapping mapping) {
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
}
