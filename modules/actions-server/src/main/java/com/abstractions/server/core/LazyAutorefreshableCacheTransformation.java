package com.abstractions.server.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

import com.core.impl.ConnectionType;
import com.service.core.ContextDefinition;
import com.service.core.ContextDefinitionTransformation;
import com.service.core.NamesMapping;
import com.service.core.ObjectDefinition;
import com.service.core.ServiceException;

public class LazyAutorefreshableCacheTransformation implements ContextDefinitionTransformation {

	private static Log log = LogFactory.getLog(LazyAutorefreshableCacheTransformation.class);

	private String objectId;
	private String memcachedURL;
	private String keyExpression;
	private String cacheExpressions;
	private int ttl;
	private NamesMapping mapping;
	
	public LazyAutorefreshableCacheTransformation(
			String objectId,
			String memcachedURL,
			String keyExpression,
			String cacheExpressions,
			int ttl,
			NamesMapping mapping) {
		
		Validate.notNull(objectId);
		Validate.notNull(memcachedURL);
		Validate.notNull(keyExpression);
		Validate.notNull(cacheExpressions);
		Validate.notNull(mapping);
		
		this.objectId = objectId;
		this.memcachedURL = memcachedURL;
		this.keyExpression = keyExpression;
		this.cacheExpressions = cacheExpressions;
		this.ttl = ttl;
		this.mapping = mapping;
	}
	
	@Override
	public void transform(ContextDefinition context) {
		//OBTAIN THE CONNECTION DEFINITION
		ObjectDefinition connectionDefinition = context.getDefinition(objectId);
		
		//SAVE THE PREVIOUS TARGET ID
		String previousTargetId = connectionDefinition.getProperty("target");
		
		//CREATE CACHE, CHOICE, CHAIN, WIRE_TAP
		ObjectDefinition getCacheDefinition = new ObjectDefinition(this.mapping.getDefinition("GET_MEMCACHED"));
		ObjectDefinition choiceDefinition = new ObjectDefinition(this.mapping.getDefinition("CHOICE"));
		ObjectDefinition chainDefinition = new ObjectDefinition(this.mapping.getDefinition("CHAIN"));
		ObjectDefinition wireTapDefinition = new ObjectDefinition(this.mapping.getDefinition("WIRE_TAP"));
		context.addDefinition(getCacheDefinition);
		context.addDefinition(choiceDefinition);
		context.addDefinition(chainDefinition);
		context.addDefinition(wireTapDefinition);
		
		//SET THE EXPRESSION TO GET FROM MEMCACHED
		String adaptedKeyExpression = "'__CACHED_" + getCacheDefinition.getId() + "_' + " + keyExpression;
		String adaptedTimeKeyExpression = "'__CACHED_" + getCacheDefinition.getId() + "_time_' + " + keyExpression;
		getCacheDefinition.setProperty("expression", adaptedKeyExpression);
		
		//CACHE -> CHOICE
		context.addConnection(getCacheDefinition.getId(), choiceDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);

		//CHOICE -> CHAIN
		String choiceConnectionId = context.addConnection(choiceDefinition.getId(), chainDefinition.getId(), ConnectionType.CHOICE_CONNECTION).getId();
		//IF CACHE IS NULL
		ObjectDefinition choiceConnectionDefinition = context.getDefinition(choiceConnectionId);
		choiceConnectionDefinition.setProperty("expression", "message.payload == null");

		//CHOICE -> WIRE_TAP
		String choiceWireTapConnectionId = context.addConnection(choiceDefinition.getId(), wireTapDefinition.getId(), ConnectionType.CHOICE_CONNECTION).getId();
		ObjectDefinition choiceWireTapConnectionDefinition = context.getDefinition(choiceWireTapConnectionId);
		choiceWireTapConnectionDefinition.setProperty("expression", "message.payload != null");

		//POINT TO THE PREVIOUS COMPUTATION
		context.addConnection(chainDefinition.getId(), previousTargetId.substring(4), ConnectionType.CHAIN_CONNECTION);
		
		//ADD PUT OPERATION
		String[] putExpressions = StringUtils.split(cacheExpressions, ';');
		if (putExpressions != null && putExpressions.length >= 1) {
			ObjectDefinition nullProcessorDefinition = new ObjectDefinition(this.mapping.getDefinition("NULL"));
			
			ObjectDefinition putCacheDefinition = new ObjectDefinition(this.mapping.getDefinition("PUT_MEMCACHED"));
			putCacheDefinition.setProperty("keyExpression", adaptedKeyExpression);
			putCacheDefinition.setProperty("valueExpression", putExpressions[0]);

			ObjectDefinition putCacheTimeDefinition = new ObjectDefinition(this.mapping.getDefinition("PUT_MEMCACHED"));
			putCacheTimeDefinition.setProperty("keyExpression", adaptedTimeKeyExpression);
			putCacheTimeDefinition.setProperty("valueExpression", "new java.util.Date().getTime()");

			context.addDefinition(nullProcessorDefinition);
			context.addDefinition(putCacheDefinition);
			context.addDefinition(putCacheTimeDefinition);
			
			context.addConnection(chainDefinition.getId(), nullProcessorDefinition.getId(), ConnectionType.CHAIN_CONNECTION);
			context.addConnection(nullProcessorDefinition.getId(), putCacheDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
			context.addConnection(putCacheDefinition.getId(), putCacheTimeDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		}
		
		//ADD WIRE TAP PART
		ObjectDefinition getTimeFromCacheDefinition = new ObjectDefinition(this.mapping.getDefinition("GET_MEMCACHED"));
		ObjectDefinition timeChoiceDefinition = new ObjectDefinition(this.mapping.getDefinition("CHOICE"));
		context.addDefinition(getTimeFromCacheDefinition);
		context.addDefinition(timeChoiceDefinition);
		getTimeFromCacheDefinition.setProperty("expression", adaptedTimeKeyExpression);

		//WIRE_TAP -> CACHE
		context.addConnection(wireTapDefinition.getId(), getTimeFromCacheDefinition.getId(), ConnectionType.WIRE_TAP_CONNECTION);
		//CACHE -> CHOICE
		context.addConnection(getTimeFromCacheDefinition.getId(), timeChoiceDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		//CHOICE -> CHAIN
		String timeChoiceId = context.addConnection(timeChoiceDefinition.getId(), chainDefinition.getId(), ConnectionType.CHOICE_CONNECTION).getId();
		
		//TIME CHOICE CONDITION
		ObjectDefinition timeChoiceConnectionDefinition = context.getDefinition(timeChoiceId);
		timeChoiceConnectionDefinition.setProperty("expression", "(message.payload != null) && ((new java.util.Date().getTime() - message.payload) > 3000)");
	
		
		//FINALLY CHANGE THE CONNECTION TO POINT TO THE GET FROM CACHE
		connectionDefinition.setProperty("target", "urn:" + getCacheDefinition.getId());
		
		try {
			context.sync();
		} catch (ServiceException e) {
			log.warn("Error syncing context", e);
		}
	}
}
