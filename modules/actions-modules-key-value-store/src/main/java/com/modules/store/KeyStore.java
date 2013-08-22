package com.modules.store;

public interface KeyStore
{

  Object put(String key, Object newValue);
  
  Object get(String key);
}
