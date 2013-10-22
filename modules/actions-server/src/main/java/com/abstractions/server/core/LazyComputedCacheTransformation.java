package com.abstractions.server.core;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.service.core.ContextDefinition;
import com.abstractions.service.core.ContextDefinitionTransformation;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;

public class LazyComputedCacheTransformation implements ContextDefinitionTransformation {

	private static Log log = LogFactory.getLog(LazyComputedCacheTransformation.class);

	private String objectId;
	private String memcachedURL;
	private String keyExpression;
	private String cacheExpressions;
	private int ttl;
	private NamesMapping mapping;
	
	public LazyComputedCacheTransformation(
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
		ObjectClazz connectionDefinition = context.getDefinition(objectId);
		
		//SAVE THE PREVIOUS TARGET ID
		String previousTargetId = connectionDefinition.getProperty("target");
		
		//CREATE CACHE, CHOICE AND CHAIN
		ObjectClazz getCacheDefinition = new ObjectClazz(this.mapping.getDefinition("GET_MEMCACHED"));
		ObjectClazz choiceDefinition = new ObjectClazz(this.mapping.getDefinition("CHOICE"));
		ObjectClazz chainDefinition = new ObjectClazz(this.mapping.getDefinition("CHAIN"));
		context.addDefinition(getCacheDefinition);
		context.addDefinition(choiceDefinition);
		context.addDefinition(chainDefinition);
		
		//SET THE EXPRESSION TO GET FROM MEMCACHED
		String adaptedKeyExpression = "'__CACHED_" + getCacheDefinition.getId() + "_' + " + keyExpression;
		getCacheDefinition.setProperty("expression", adaptedKeyExpression);
		
		//CACHE -> CHOICE
		context.addConnection(getCacheDefinition.getId(), choiceDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);

		//CHOICE -> CHAIN
		String choiceConnectionId = context.addConnection(choiceDefinition.getId(), chainDefinition.getId(), ConnectionType.CHOICE_CONNECTION).getId();
		//IF CACHE IS NULL
		ObjectClazz choiceConnectionDefinition = context.getDefinition(choiceConnectionId);
		choiceConnectionDefinition.setProperty("expression", "message.payload == null");

		//POINT TO THE PREVIOUS COMPUTATION
		context.addConnection(chainDefinition.getId(), previousTargetId.substring(4), ConnectionType.CHAIN_CONNECTION);
		
		//ADD PUT OPERATION
		String[] putExpressions = StringUtils.split(cacheExpressions, ';');
		if (putExpressions != null && putExpressions.length >= 1) {
			ObjectClazz nullProcessorDefinition = new ObjectClazz(this.mapping.getDefinition("NULL"));
			
			ObjectClazz putCacheDefinition = new ObjectClazz(this.mapping.getDefinition("PUT_MEMCACHED"));
			putCacheDefinition.setProperty("keyExpression", adaptedKeyExpression);
			putCacheDefinition.setProperty("valueExpression", putExpressions[0]);

			context.addDefinition(nullProcessorDefinition);
			context.addDefinition(putCacheDefinition);
			context.addConnection(chainDefinition.getId(), nullProcessorDefinition.getId(), ConnectionType.CHAIN_CONNECTION);
			context.addConnection(nullProcessorDefinition.getId(), putCacheDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		}
		
		//FINALLY CHANGE THE CONNECTION TO POINT TO THE GET FROM CACHE
		connectionDefinition.setProperty("target", "urn:" + getCacheDefinition.getId());
		
		try {
			context.sync();
		} catch (ServiceException e) {
			log.warn("Error syncing context", e);
		}
	}
}
