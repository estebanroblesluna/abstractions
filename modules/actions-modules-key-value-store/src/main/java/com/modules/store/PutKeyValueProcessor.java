package com.modules.store;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.api.Processor;


public class PutKeyValueProcessor implements Processor
{

  private KeyStore store;
  private Expression keyExpression;
  private Expression valueExpression;
  private boolean replacePayload;

  public PutKeyValueProcessor(KeyStore store, Expression keyExpression, Expression valueExpression)
  {
    this.store = store;
    this.keyExpression = keyExpression;
    this.valueExpression = valueExpression;
    this.replacePayload = false;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    String key = this.keyExpression.evaluate(message).toString();
    Object value = this.valueExpression.evaluate(message);
    Object oldValue = this.store.put(key, value);
    
    if (this.replacePayload)
    {
      message.setPayload(oldValue);
    }
    
    return message;
  }
}
