package com.abstractions.instance.common;

import org.apache.commons.lang.Validate;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.Processor;


public class ExpressionPayloadReplacerProcessor implements Processor
{

  private Expression payloadExpression;
  
  public ExpressionPayloadReplacerProcessor(Expression payloadExpression)
  {
    Validate.notNull(payloadExpression);
    
    this.payloadExpression = payloadExpression;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    Object value = this.payloadExpression.evaluate(message);
    message.setPayload(value);
    return message;
  }
}
