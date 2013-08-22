package com.test;

import java.util.ArrayList;
import java.util.List;

import com.core.api.Message;
import com.core.api.Processor;

public class ScoreAndMergeProcessor implements Processor
{
  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    if (message.getPayload() instanceof List)
    {
      List<ScrapingResult> results = this.getScrapingResults(message);
      ScrapingResult result = this.merge(results);
      message.setPayload(result);
    }
    else if (!(message.getPayload() instanceof ScrapingResult))
    {
      message.setPayload(new ScrapingResult());
    }

    return message;
  }

  private ScrapingResult merge(List<ScrapingResult> results)
  {
    ScrapingResult result = new ScrapingResult();
    
    for (ScrapingResult scraping : results)
    {
      result = result.merge(scraping);
    }
    
    return result;
  }

  private List<ScrapingResult> getScrapingResults(Message message)
  {
    List<Message> messages = (List<Message>) message.getPayload();
    List<ScrapingResult> results = new ArrayList<ScrapingResult>();

    for (Message ogMessage : messages)
    {
      if (ogMessage.getPayload() != null
          && ogMessage.getPayload() instanceof ScrapingResult)
      {
        results.add((ScrapingResult) ogMessage.getPayload());
      }
    }

    return results;
  }
}
