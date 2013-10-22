package com.abstractions.instance.common;

import com.abstractions.api.Message;
import com.abstractions.api.Processor;


public class NullProcessor implements Processor
{

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    return message;
  }
}
