package com.modules.cache;

public interface Cache
{

  Object get(String key);
  
  Object put(String key, Object newValue);

}
