package com.modules.kafka;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.Processor;


public class KafkaProducerProcessor implements Processor
{
  private String queue;
  private String zookeeper;
  private Expression expression;
  
  public KafkaProducerProcessor(String queue, String zookeeper)
  {
    this.queue = queue;
    this.zookeeper = zookeeper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Message process(Message message)
  {
    Object result = this.expression.evaluate(message);
    this.write(result);
    return message;
  }

  private void write(Object result)
  {
  }
}
