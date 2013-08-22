package com.modules.cache;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCache implements Cache
{
  private Map<String, Object> store;
  
  public InMemoryCache()
  {
    this.store = new HashMap<String, Object>();
  }
  
  @Override
  public Object put(String key, Object newValue)
  {
    Object oldValue;
    
    synchronized (this.store)
    {
      oldValue = this.store.put(key, newValue);
    }
    
    return oldValue;
  }

  @Override
  public Object get(String key)
  {
    synchronized (this.store)
    {
      return this.store.get(key);
    }
  }
}
