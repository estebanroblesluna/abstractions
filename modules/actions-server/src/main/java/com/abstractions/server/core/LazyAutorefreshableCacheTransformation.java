package com.abstractions.server.core;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.meta.CompositeDefinition;
import com.abstractions.service.core.ApplicationTransformation;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;
import com.modules.cache.MemcachedCache;

public class LazyAutorefreshableCacheTransformation implements ApplicationTransformation {

	private static Log log = LogFactory.getLog(LazyAutorefreshableCacheTransformation.class);

	private String objectId;
	private String memcachedURL;
	private String keyExpression;
	private String cacheExpressions;
	private int ttl;
	private int oldCacheEntryInMills; // could be every 3secs -> 3000
	private NamesMapping mapping;
	
	public LazyAutorefreshableCacheTransformation(
			String objectId,
			String memcachedURL,
			String keyExpression,
			String cacheExpressions,
			int oldCacheEntryInMills,
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
		this.oldCacheEntryInMills = oldCacheEntryInMills;
		this.ttl = this.oldCacheEntryInMills * 10;
		this.mapping = mapping;
	}
	
	@Override
	public void transform(CompositeTemplate context, ApplicationTemplate appTemplate) {
		List<ElementTemplate> cacheAccess = new ArrayList<ElementTemplate>();
		
		//OBTAIN THE CONNECTION DEFINITION
		ElementTemplate connectionDefinition = context.getDefinition(objectId);
		
		//SAVE THE PREVIOUS TARGET ID
		String previousTargetId = connectionDefinition.getProperty("target");
		
		//CREATE CACHE, CHOICE, CHAIN, WIRE_TAP
		ElementTemplate getCacheDefinition = new ElementTemplate(this.mapping.getDefinition("GET_MEMCACHED"));
		cacheAccess.add(getCacheDefinition);
		ElementTemplate choiceDefinition = new ElementTemplate(this.mapping.getDefinition("CHOICE"));
		ElementTemplate chainDefinition = new ElementTemplate(this.mapping.getDefinition("CHAIN"));
		ElementTemplate wireTapDefinition = new ElementTemplate(this.mapping.getDefinition("WIRE_TAP"));
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
		ElementTemplate choiceConnectionDefinition = context.getDefinition(choiceConnectionId);
		choiceConnectionDefinition.setProperty("expression", "message.payload == null");

		//CHOICE -> WIRE_TAP
		String choiceWireTapConnectionId = context.addConnection(choiceDefinition.getId(), wireTapDefinition.getId(), ConnectionType.CHOICE_CONNECTION).getId();
		ElementTemplate choiceWireTapConnectionDefinition = context.getDefinition(choiceWireTapConnectionId);
		choiceWireTapConnectionDefinition.setProperty("expression", "message.payload != null");

		//POINT TO THE PREVIOUS COMPUTATION
		context.addConnection(chainDefinition.getId(), previousTargetId.substring(4), ConnectionType.CHAIN_CONNECTION);
		
		//ADD PUT OPERATION
		String[] putExpressions = StringUtils.split(cacheExpressions, ';');
		if (putExpressions != null && putExpressions.length >= 1) {
			ElementTemplate nullProcessorDefinition = new ElementTemplate(this.mapping.getDefinition("NULL"));
			
			ElementTemplate putCacheDefinition = new ElementTemplate(this.mapping.getDefinition("PUT_MEMCACHED"));
			putCacheDefinition.setProperty("keyExpression", adaptedKeyExpression);
			putCacheDefinition.setProperty("valueExpression", putExpressions[0]);

			ElementTemplate putCacheTimeDefinition = new ElementTemplate(this.mapping.getDefinition("PUT_MEMCACHED"));
			putCacheTimeDefinition.setProperty("keyExpression", adaptedTimeKeyExpression);
			putCacheTimeDefinition.setProperty("valueExpression", "new java.util.Date().getTime()");

			cacheAccess.add(putCacheDefinition);
			cacheAccess.add(putCacheTimeDefinition);

			context.addDefinition(nullProcessorDefinition);
			context.addDefinition(putCacheDefinition);
			context.addDefinition(putCacheTimeDefinition);
			
			context.addConnection(chainDefinition.getId(), nullProcessorDefinition.getId(), ConnectionType.CHAIN_CONNECTION);
			context.addConnection(nullProcessorDefinition.getId(), putCacheDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
			context.addConnection(putCacheDefinition.getId(), putCacheTimeDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		}
		
		//ADD WIRE TAP PART
		ElementTemplate getTimeFromCacheDefinition = new ElementTemplate(this.mapping.getDefinition("GET_MEMCACHED"));
		ElementTemplate timeChoiceDefinition = new ElementTemplate(this.mapping.getDefinition("CHOICE"));
		context.addDefinition(getTimeFromCacheDefinition);
		context.addDefinition(timeChoiceDefinition);
		getTimeFromCacheDefinition.setProperty("expression", adaptedTimeKeyExpression);

		cacheAccess.add(getTimeFromCacheDefinition);

		//WIRE_TAP -> CACHE
		context.addConnection(wireTapDefinition.getId(), getTimeFromCacheDefinition.getId(), ConnectionType.WIRE_TAP_CONNECTION);
		//CACHE -> CHOICE
		context.addConnection(getTimeFromCacheDefinition.getId(), timeChoiceDefinition.getId(), ConnectionType.NEXT_IN_CHAIN_CONNECTION);
		//CHOICE -> CHAIN
		String timeChoiceId = context.addConnection(timeChoiceDefinition.getId(), chainDefinition.getId(), ConnectionType.CHOICE_CONNECTION).getId();
		
		//TIME CHOICE CONDITION
		ElementTemplate timeChoiceConnectionDefinition = context.getDefinition(timeChoiceId);
		timeChoiceConnectionDefinition.setProperty("expression", "(message.payload != null) && ((new java.util.Date().getTime() - message.payload) > " + this.oldCacheEntryInMills + ")");
	
		
		//FINALLY CHANGE THE CONNECTION TO POINT TO THE GET FROM CACHE
		connectionDefinition.setProperty("target", "urn:" + getCacheDefinition.getId());
		
		//CREATE THE MEMCACHED CACHE
		MemcachedCache cache = new MemcachedCache();
		cache.setServerAddresses(this.memcachedURL);
		cache.setTtl(this.ttl);
		
		//INITIALIZE ALL OBJECTS THAT HAS ACCESS TO THE CACHE
		CompositeDefinition appDefinition = ((CompositeDefinition) context.getMeta());
		appDefinition.initializeTemplates(context, null, this.mapping, cacheAccess, appTemplate);
		
		for (ElementTemplate template : cacheAccess) {
			try {
				BeanUtils.setProperty(template.getInstance(), "cache", cache);
			} catch (IllegalAccessException e) {
				log.warn("Error setting the cache", e);
			} catch (InvocationTargetException e) {
				log.warn("Error setting the cache", e);
			}
		}
		
		try {
		  appTemplate.sync();
		} catch (ServiceException e) {
			log.warn("Error syncing context", e);
		}
	}
}
