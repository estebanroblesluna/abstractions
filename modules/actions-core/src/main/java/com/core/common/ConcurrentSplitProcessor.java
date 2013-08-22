package com.core.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.Validate;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.ProcessingException;
import com.core.api.Processor;


public class ConcurrentSplitProcessor implements Processor
{
  private Expression listExpression;
  private Processor nextProcessor;
  private ExecutorService service;

  public ConcurrentSplitProcessor(Expression listExpression, Processor nextProcessor)
  {
    Validate.notNull(listExpression);
    Validate.notNull(nextProcessor);
    
    this.listExpression = listExpression;
    this.nextProcessor = nextProcessor;
    this.service = Executors.newFixedThreadPool(10);
  }
  
  @Override
  @SuppressWarnings("rawtypes")
  public Message process(final Message message)
  {
    List list = (List) this.listExpression.evaluate(message);

    final CountDownLatch latch = new CountDownLatch(list.size());
    final List<Message> results = new ArrayList<Message>();
    
    for (final Object object : list)
    {
      this.service.execute(new Runnable()
      {
        @Override
        public void run()
        {
          Message newMessage = message.clone();
          newMessage.setPayload(object);

          Message result = null;
          try
          {
            result = nextProcessor.process(newMessage);
          }
          catch (Exception e)
          {
            newMessage.setPayload(e);
            result = message;
          }
          finally
          {
            synchronized (results)
            {
              results.add(result);
            }
            latch.countDown();
          }
        }
      });
    }
    
    try
    {
      latch.await();
      message.setPayload(results);
      return message;
    }
    catch (InterruptedException e)
    {
      throw new ProcessingException(e);
    }
  }
  
}
