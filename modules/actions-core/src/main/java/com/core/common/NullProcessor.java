package com.core.common;

import com.core.api.Message;
import com.core.api.Processor;


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
