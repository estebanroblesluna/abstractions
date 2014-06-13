package com.abstractions.api;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Message implements Cloneable {

  private final Map<String, Object> properties;
  private volatile Object payload;
  private volatile Exception exception;

  public Message() {
    this.properties = new HashMap<String, Object>();
    this.payload = null;
    this.addCreationalProperty();
  }

  public Message(Object payload) {
    this();
    this.payload = payload;
  }

  public Message(Map<String, Object> properties, Object payload) {
    this(payload);
    this.properties.putAll(properties);
    this.addCreationalProperty();
  }

  public Message(Map<String, Object> properties, Object payload, Exception e) {
    this(properties, payload);
    this.exception = e;
  }

  private void addCreationalProperty() {
    this.putProperty("createTime", new Date().getTime());
  }

  public Object getProperty(String key) {
    return this.properties.get(key);
  }

  public Object getPayload() {
    return payload;
  }

  public Map<String, Object> getProperties() {
    return Collections.synchronizedMap(this.properties);
  }

  public Message clone() {
    return new Message(this.properties, this.payload, this.exception);
  }

  public void putProperty(String key, Object value) {
    synchronized (this.properties) {
      this.properties.put(key, value);
    }
  }

  public void setPayload(Object payload) {
    this.payload = payload;
  }

  public void overrideAllFrom(Message message) {
    synchronized (this.properties) {
      this.properties.putAll(message.getProperties());
    }
    this.payload = message.getPayload();
    this.exception = message.getException();
  }

  public Exception getException() {
    return exception;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }
}
