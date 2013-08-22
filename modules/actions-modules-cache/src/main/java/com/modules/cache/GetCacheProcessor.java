package com.modules.cache;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.Processor;


public class GetCacheProcessor implements Processor
{
  private Cache cache;
  private Expression expression;
  
  public GetCacheProcessor(Cache cache, Expression expression)
  {
    this.cache = cache;
    this.expression = expression;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    String key = this.expression.evaluate(message).toString();
    Object result = this.cache.get(key);
    message.setPayload(result);
    return message;
  }
}
