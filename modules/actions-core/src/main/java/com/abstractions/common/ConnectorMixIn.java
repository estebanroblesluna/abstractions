package com.abstractions.common;

import org.codehaus.jackson.annotate.JsonTypeInfo;

/**
 * @author Martin Aparicio Pons (martin.aparicio.pons@gmail.com)
 * 
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public interface ConnectorMixIn {

}
