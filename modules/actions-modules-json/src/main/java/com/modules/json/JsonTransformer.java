package com.modules.json;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.abstractions.api.Message;
import com.abstractions.api.ProcessingException;
import com.abstractions.api.Processor;

public class JsonTransformer implements Processor
{

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    try
    {
      JSONObject json = null;

      if (message.getPayload() instanceof InputStream)
      {
        InputStream io = (InputStream) message.getPayload();
        String jsonAsString = IOUtils.toString(io);
        json = new JSONObject(jsonAsString);
        IOUtils.closeQuietly(io);
      }
      else if (message.getPayload() instanceof String)
      {
        json = new JSONObject((String) message.getPayload());
      }
      else
      {
        throw new ProcessingException("Invalid type for payload");
      }

      message.setPayload(json);
      return message;
    }
    catch (JSONException e)
    {
      throw new ProcessingException(e);
    }
    catch (IOException e)
    {
      throw new ProcessingException(e);
    }
  }

}
