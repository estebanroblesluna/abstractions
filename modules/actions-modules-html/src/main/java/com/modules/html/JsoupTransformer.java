package com.modules.html;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.api.ProcessingException;
import com.abstractions.api.Processor;

public class JsoupTransformer implements Processor
{
  private static Log log = LogFactory.getLog(JsoupTransformer.class);

  private Expression urlExpression;
  
  public JsoupTransformer()
  {
  }

  public JsoupTransformer(Expression urlExpression)
  {
    Validate.notNull(urlExpression);
    
    this.urlExpression = urlExpression;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    String url = null;
    if (this.urlExpression != null)
    {
      url = this.urlExpression.evaluate(message).toString();
    }
    
    Document document;
    try
    {
      if (message.getPayload() instanceof InputStream)
      {
        if (url != null)
        {
          document = Jsoup.parse((InputStream) message.getPayload(), null, url);
        }
        else
        {
          throw new ProcessingException("Url is required if the payload is an InputStream");
        }
      }
      else
      {
        if (url != null)
        {
          document = Jsoup.parse(message.getPayload().toString(), url);
        }
        else
        {
          document = Jsoup.parse(message.getPayload().toString());
        }
      }
    }
    catch (IOException e)
    {
      log.error(e);
      throw new ProcessingException(e);
    }

    message.setPayload(document);
    
    return message;
  }

}
