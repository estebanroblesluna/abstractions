package com.modules.image;

import java.io.IOException;
import java.io.InputStream;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

import com.abstractions.api.Message;
import com.abstractions.api.ProcessingException;
import com.abstractions.api.Processor;

public class ImageReaderProcessor implements Processor
{

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    ImageInfo info = null;

    try
    {
      if (message.getPayload() instanceof InputStream)
      {
        info = Sanselan.getImageInfo((InputStream) message.getPayload(), null);
      }
      else if (message.getPayload() instanceof byte[])
      {
        info = Sanselan.getImageInfo((byte[]) message.getPayload());
      }
    }
    catch (ImageReadException e)
    {
      throw new ProcessingException(e);
    }
    catch (IOException e)
    {
      throw new ProcessingException(e);
    }

    if (info != null)
    {
      message.setPayload(info);
    }

    return message;
  }

}
