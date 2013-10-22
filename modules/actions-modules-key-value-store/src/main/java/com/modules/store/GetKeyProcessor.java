package com.modules.store;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.api.Processor;


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
