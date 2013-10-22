package com.abstractions.api;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Message implements Cloneable
{
  private Map<String, Object> properties;
  private Object              payload;

  public Message()
  {
    this.properties = new HashMap<String, Object>();
    this.payload = null;
    this.addCreationalProperty();
  }

  public Message(Object payload)
  {
    this();
    this.payload = payload;
  }

  public Message(Map<String, Object> properties, Object payload)
  {
    this(payload);
    this.properties.putAll(properties);
    this.addCreationalProperty();
  }
  
  private void addCreationalProperty()
  {
	  this.properties.put("createTime", new Date().getTime());
  }
  
  public Object getProperty(String key)
  {
    return this.properties.get(key);
  }

  public Object getPayload()
  {
    return payload;
  }
  
  public Map<String, Object> getProperties()
  {
    return this.properties;
  }
  
  public Message clone()
  {
    return new Message(this.properties, this.payload);
  }

  public void putProperty(String key, Object value)
  {
    this.properties.put(key, value);
  }

  public void setPayload(Object payload)
  {
    this.payload = payload;
  }

  public void overrideAllFrom(Message message)
  {
    synchronized (this.properties)
    {
      this.properties.putAll(message.getProperties());
    }
    this.payload = message.getPayload();
  }
}
