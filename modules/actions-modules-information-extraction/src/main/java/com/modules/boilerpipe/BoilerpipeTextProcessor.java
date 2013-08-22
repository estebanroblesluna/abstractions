package com.modules.boilerpipe;

import java.io.InputStream;
import java.io.InputStreamReader;

import com.core.api.Message;
import com.core.api.ProcessingException;
import com.core.api.Processor;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

public class BoilerpipeTextProcessor implements Processor
{

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    try
    {
      if (message.getPayload() instanceof String)
      {
        String text = ArticleExtractor.INSTANCE.getText((String) message.getPayload());
        message.setPayload(text);
      }
      else if (message.getPayload() instanceof InputStream)
      {
        InputStreamReader reader = new InputStreamReader((InputStream) message.getPayload());
        String text = ArticleExtractor.INSTANCE.getText(reader);
        message.setPayload(text);
      }
    }
    catch (BoilerpipeProcessingException e)
    {
      throw new ProcessingException(e);
    }

    return message;
  }
}
