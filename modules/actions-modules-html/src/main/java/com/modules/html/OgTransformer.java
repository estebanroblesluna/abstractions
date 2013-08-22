package com.modules.html;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.core.api.Message;
import com.core.api.Processor;

public class OgTransformer implements Processor
{

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    if (message.getPayload() instanceof Document)
    {
      Document document = (Document) message.getPayload();
      Elements ogElements = document.select("meta[property^=og:]");

      Map<String, String> result = new HashMap<String, String>();
      
      for (Element ogElement : ogElements)
      {
        String propertyName = ogElement.attr("property");
        String propertyValue = ogElement.attr("content");

        result.put(propertyName, propertyValue);
      }

      message.setPayload(result);
    }
    
    return message;
  }

}
