package com.abstractions.common;

import org.codehaus.jackson.annotate.JsonTypeInfo;
/**
 * This interface is created so that it's possible
 * to put json important info for serialization/deserialization
 * but without touching the actual class (ElementDefinition)
 * 
 * @author Guido J. Celada
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="@type")
public interface ElementDefinitionMixIn {

}
