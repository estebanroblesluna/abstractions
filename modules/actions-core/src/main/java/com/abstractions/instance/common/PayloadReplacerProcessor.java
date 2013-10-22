package com.abstractions.instance.common;

import com.core.api.Message;
import com.core.api.Processor;


public class PayloadReplacerProcessor implements Processor
{

  private Object payload;

  public PayloadReplacerProcessor(Object payload)
  {
    this.payload = payload;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    message.setPayload(this.payload);
    return message;
  }
}
