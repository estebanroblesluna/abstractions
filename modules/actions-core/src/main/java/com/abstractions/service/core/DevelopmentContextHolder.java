package com.abstractions.service.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.model.User;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class DevelopmentContextHolder {

  private static final Log log = LogFactory.getLog(DevelopmentContextHolder.class);

  public static final String APP_DEFINITION = "APP_DEFINITION";
  public static final String APP_TEMPLATE = "APP_TEMPLATE";

  private final LoadingCache<String, Map<String, Object>> appDefinitions;

  public DevelopmentContextHolder(long duration, TimeUnit unit, final ApplicationDefinitionLoader loader, final NamesMapping mapping) {
    this.appDefinitions = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .maximumSize(10000)
            .expireAfterAccess(duration, unit)
            .removalListener(new RemovalListener<String, Map<String, Object>>() {
              @Override
              public void onRemoval(RemovalNotification<String, Map<String, Object>> notification) {
                Map<String, Object> objects = notification.getValue();
                ApplicationTemplate appTemplate = (ApplicationTemplate) objects.get(APP_TEMPLATE);
                appTemplate.terminate();
              }
            })
            .build(new CacheLoader<String, Map<String, Object>>() {
              @Override
              public Map<String, Object> load(String key) throws Exception {
                Map<String, Object> objects = new HashMap<String, Object>();
                ApplicationDefinition appDefinition = loader.load(Long.valueOf(key.split("-")[1]), mapping);
                ApplicationTemplate appTemplate = appDefinition.createTemplate(mapping);
                objects.put(APP_TEMPLATE, appTemplate);
                objects.put(APP_DEFINITION, appDefinition);
                return objects;
              }
    });
  }

  public List<ApplicationTemplate> getApplicationTemplatesOf(Long applicationId) {
    List<ApplicationTemplate> templates = new ArrayList<ApplicationTemplate>();
    Map<String, Map<String, Object>> defs = this.appDefinitions.asMap();
    
    for (Map.Entry<String, Map<String, Object>> entry : defs.entrySet()) {
      if (entry.getKey().endsWith(applicationId.toString())) {
        ApplicationTemplate appTemplate = (ApplicationTemplate) entry.getValue().get(APP_TEMPLATE);
        templates.add(appTemplate);
      }
    }
    
    return templates;
  }
  
  public List<Map<String, Object>> getApplicationMaps(Long applicationId) {
    List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
    Map<String, Map<String, Object>> defs = this.appDefinitions.asMap();
    
    for (Map.Entry<String, Map<String, Object>> entry : defs.entrySet()) {
      if (entry.getKey().endsWith(applicationId.toString())) {
        maps.add(entry.getValue());
      }
    }
    
    return maps;
  }
  
  public ApplicationTemplate getApplicationTemplate(User user, Long applicationId) {
    return this.getApplicationTemplate(user, applicationId.toString());
  }

  public ApplicationTemplate getApplicationTemplate(User user, String applicationId) {
    try {
      String key = user.getId() + "-" + applicationId;
      ApplicationTemplate appTemplate = (ApplicationTemplate) this.appDefinitions.get(key).get(APP_TEMPLATE);
      if (appTemplate == null) {
        log.warn("No application template found for id: " + StringUtils.defaultString(applicationId));
      }
      return appTemplate;
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }

  public ApplicationDefinition getApplication(User user, String applicationId) {
    return this.getApplication(user, Long.valueOf(applicationId));
  }

  public ApplicationDefinition getApplication(User user, Long applicationId) {
    try {
      String key = user.getId() + "-" + applicationId;
      ApplicationDefinition appDefinition = (ApplicationDefinition) this.appDefinitions.get(key).get(APP_DEFINITION);
      if (appDefinition == null) {
        log.warn("No application template found for id: " + StringUtils.defaultString(applicationId.toString()));
      }
      return appDefinition;
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
