package com.modules.cache;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.Processor;


public class PutCacheProcessor implements Processor
{

  private Cache cache;
  private Expression keyExpression;
  private Expression valueExpression;
  private boolean replacePayload;

  public PutCacheProcessor(Cache cache, Expression keyExpression, Expression valueExpression)
  {
    this.cache = cache;
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
    Object oldValue = this.cache.put(key, value);
    
    if (this.replacePayload)
    {
      message.setPayload(oldValue);
    }
    
    return message;
  }
}
