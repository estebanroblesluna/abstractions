package com.test;

import com.core.api.Message;
import com.core.api.Processor;

public class BoilerpipeResultTransformer implements Processor
{

  @Override
  public Message process(Message message)
  {
    ScrapingResult result = new ScrapingResult();
    
    if (message.getPayload() instanceof String)
    {
      //this is the full text so go ahead and give it a good score
      result.setFullText((String) message.getPayload());
      result.setFullTextScore(.8);
    }

    message.setPayload(result);
    return message;
  }

}
