package com.abstractions.service.core;

import com.abstractions.model.Environment;


public class EnvironmentHolder {

  private static Environment currentEnvironment;
  
  public static void initialize(Environment env) {
    currentEnvironment = env;
  }
  
  private EnvironmentHolder() {
  }

  public static Environment getCurrentEnvironment() {
    if (currentEnvironment == null) {
      currentEnvironment = Environment.DEV;
    }
    return currentEnvironment;
  }
  
  public static boolean isDev() {
    return getCurrentEnvironment().equals(Environment.DEV);
  }
}
