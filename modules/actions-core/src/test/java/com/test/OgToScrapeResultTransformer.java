package com.test;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.core.api.Message;
import com.core.api.Processor;

public class OgToScrapeResultTransformer implements Processor
{

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    ScrapingResult result = new ScrapingResult();
    
    if (message.getPayload() instanceof Map)
    {
      Map<String, String> ogs = (Map<String, String>) message.getPayload();
      
      String ogTitle = ogs.get("og:title");
      if (!StringUtils.isBlank(ogTitle))
      {
        result.setTitle(ogTitle);
        result.setTitleScore(1);
      }
      
      String ogDescription = ogs.get("og:description");
      if (!StringUtils.isBlank(ogDescription))
      {
        result.setDescription(ogDescription);
        result.setDescriptionScore(1);
        
        result.setFullText(ogDescription);
        result.setFullTextScore(.2);
      }
    }
    
    message.setPayload(result);
    return message;
  }

}
