package com.abstractions.instance.common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.abstractions.api.Message;
import com.abstractions.api.Processor;


public class ToStringProcessor implements Processor
{

  @Override
  public Message process(Message message)
  {
    if (message.getPayload() instanceof byte[])
    {
      String string = new String((byte[]) message.getPayload());
      message.setPayload(string);
    }
    else if (message.getPayload() instanceof InputStream)
    {
      try
      {
        InputStream io = (InputStream) message.getPayload();
        String string = IOUtils.toString(io);
        IOUtils.closeQuietly(io);
        message.setPayload(string);
      }
      catch (IOException e)
      {
        // TODO log
      }
    }
    
    return message;
  }

}
