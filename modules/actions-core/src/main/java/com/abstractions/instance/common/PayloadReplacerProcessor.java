package com.abstractions.instance.common;

import com.abstractions.api.Message;
import com.abstractions.api.Processor;


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
