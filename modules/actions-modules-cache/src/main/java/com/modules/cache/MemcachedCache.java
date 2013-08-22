package com.modules.cache;

import java.io.IOException;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class MemcachedCache implements Cache
{

  private MemcachedClient client;

  public MemcachedCache(String serverAddresses)
  {
    try
    {
      this.client =
          new MemcachedClient(new BinaryConnectionFactory(),
                              AddrUtil.getAddresses(serverAddresses));
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Object get(String key)
  {
    Object value = this.client.get(key);
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object put(String key, Object newValue)
  {
    this.client.set(key, 3600, newValue);
    return null;
  }
}
