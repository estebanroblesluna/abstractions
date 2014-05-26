package com.abstractions.utils;

import com.abstractions.cdn.CdnStatusManager;


public class LiquidMLWebApplicationContext {

  private static LiquidMLWebApplicationContext instance;
  
  public static LiquidMLWebApplicationContext getInstance() {
    if (instance == null) {
      instance = new LiquidMLWebApplicationContext();
    }
    return instance;
  }
  
  public String getCdnUrl(long applicationId) {
    return ((CdnStatusManager) ApplicationContextHolder.getInstance().getBean("cdnStatusManager"))
            .getCdnForApplication(applicationId);
  }
  
}
