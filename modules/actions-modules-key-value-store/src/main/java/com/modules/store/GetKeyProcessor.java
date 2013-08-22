package com.modules.store;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.Processor;


public class GetKeyProcessor implements Processor
{
  private KeyStore store;
  private Expression keyExpression;
  
  public GetKeyProcessor(KeyStore store, Expression keyExpression)
  {
    this.store = store;
    this.keyExpression = keyExpression;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    Object key = this.keyExpression.evaluate(message);
    Object result = this.store.get(key.toString());
    message.setPayload(result);
    return message;
  }
}
