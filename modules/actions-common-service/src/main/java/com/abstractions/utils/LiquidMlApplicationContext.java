package com.abstractions.utils;

public class LiquidMlApplicationContext {

  private static LiquidMlApplicationContext instance;
  
  public static LiquidMlApplicationContext getInstance() {
    if (instance == null) {
      instance = new LiquidMlApplicationContext();
    }
    return instance;
  }
  
  public String getLocalCdnBaseUrlFor(long applicationId) {
    String url = (String) ApplicationContextHolder.getInstance().getProperty("localCdnBaseUrl");
    url = url.replaceAll("\\{applicationId\\}", new Long(applicationId).toString());
    return url;
  }
  
  public Integer getLocalCdnPort() {
    return Integer.parseInt(ApplicationContextHolder.getInstance().getProperty("localCdnPort"));
  }
  
}
