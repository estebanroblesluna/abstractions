package com.abstractions.service.core;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang.StringUtils;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Expression;
import com.abstractions.expression.ScriptingExpression;
import com.abstractions.expression.ScriptingLanguage;

public class BeanUtils {

	public static void setProperty(Object object, String propertyName, String propertyValue, CompositeElement context, NamesMapping mapping) throws ServiceException {
		try {
			org.apache.commons.beanutils.BeanUtils.setProperty(object, propertyName, propertyValue);
		} catch (IllegalArgumentException e) {
			if (propertyValue != null) {
				if (propertyValue.startsWith("urn:")) {
					setReference(object, propertyName, propertyValue, context);
				} else if (propertyValue.startsWith("expression:")) {
					setExpression(object, propertyName, propertyValue, mapping);
				} else if (propertyValue.startsWith("list:")) {
					setList(object, propertyName, propertyValue, mapping);
				} else {
					try {
						PropertyDescriptor descriptor = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptor(object, propertyName);
						if (Enum.class.isAssignableFrom(descriptor.getPropertyType())) {
							setEnum(object, propertyValue, descriptor);
						} else if (Expression.class.isAssignableFrom(descriptor.getPropertyType())) {
							String newValue = "expression:groovy:" + StringUtils.defaultString(propertyValue);
							setExpression(object, propertyName, newValue, mapping);
						}
					} catch (IllegalAccessException e1) {
						throw new ServiceException("Error setting the property");
					} catch (InvocationTargetException e1) {
						throw new ServiceException("Error setting the property");
					} catch (NoSuchMethodException e1) {
						throw new ServiceException("Error setting the property");
					}
				}
			}
				
		} catch (IllegalAccessException e) {
			throw new ServiceException("Error setting the property");
		} catch (InvocationTargetException e) {
			throw new ServiceException("Error setting the property");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void setEnum(Object object, String propertyValue,
			PropertyDescriptor descriptor) throws IllegalAccessException,
			InvocationTargetException, ServiceException {
		Class<Enum> theClass = (Class<Enum>) descriptor.getPropertyType();
		try {
			Enum value = Enum.valueOf(theClass, propertyValue);
			descriptor.getWriteMethod().invoke(object, value);
		} catch (IllegalArgumentException e2) {
			throw new ServiceException("Error setting the property");
		}
	}
	
	/**
	 * Example
	 *   list:(urn:aaasdas-da-sd-asd-,urn:121341-341-34,urn:124134-134-124-12-4)
	 *   list:(expression:GROOVY:message.getPayload(''),expression:GROOVY:return "")
	 */
	private static void setList(Object object, String propertyName, String propertyValue, NamesMapping mapping) {
		// TODO Auto-generated method stub
		
	}

	private static void setExpression(Object object, String propertyName, String propertyValue, NamesMapping mapping) throws ServiceException {
		String expressionAsString = propertyValue.substring("expression:".length());
		int index = expressionAsString.indexOf(':');
		String languageAsString = expressionAsString.substring(0, index);
		String expressionPart = expressionAsString.substring(index + 1);

		ScriptingLanguage language = mapping.getLanguage(languageAsString);
		ScriptingExpression expression = new ScriptingExpression(language, expressionPart);

		try {
			org.apache.commons.beanutils.BeanUtils.setProperty(object, propertyName, expression);
		} catch (IllegalAccessException e1) {
			throw new ServiceException("Error setting the property");
		} catch (InvocationTargetException e1) {
			throw new ServiceException("Error setting the property");
		}
	}

	private static void setReference(Object object, String propertyName, String propertyValue, CompositeElement context) throws ServiceException {
		String referenceId = propertyValue.substring("urn:".length());
		Object referredObject = context.getObjectWithId(referenceId);
		try {
			org.apache.commons.beanutils.BeanUtils.setProperty(object, propertyName, referredObject);
		} catch (IllegalAccessException e1) {
			throw new ServiceException("Error setting the property");
		} catch (InvocationTargetException e1) {
			throw new ServiceException("Error setting the property");
		}
	}

	public static String getUrnsFromListAsString(String listOfUrns) {
		if (StringUtils.isEmpty(listOfUrns)) {
			return null;
		}
		String urns = listOfUrns.substring("list(".length(), listOfUrns.length() - 1);
		return urns;
	}
	
	public static List<String> getUrnsFromList(String listOfUrns) {
		String urns = getUrnsFromListAsString(listOfUrns);
		if (urns == null) {
			return new ArrayList<String>();
		} else {
			return Arrays.asList(StringUtils.split(urns, ','));
		}
	}

	public static String asUrn(String string) {
		return string.startsWith("urn:") ? string : "urn:" + string;
	}
}
