package com.abstractions.instance.common;

import com.abstractions.api.Message;
import com.abstractions.api.Processor;


public class AddPropertyProcessor implements Processor
{
  private String key;
  private Processor processor;
  
  public AddPropertyProcessor()
  {
	  this("", null);
  }
  
  public AddPropertyProcessor(String key, Processor processor)
  {
    this.key = key;
    this.processor = processor;
  }

  @Override
  public Message process(Message message)
  {
    Message clonedMessage = message.clone();
    Message resultMessage = this.processor.process(clonedMessage);
    message.putProperty(this.key, resultMessage.getPayload());
    return message;
  }

public String getKey() {
	return key;
}

public void setKey(String key) {
	this.key = key;
}

public Processor getProcessor() {
	return processor;
}

public void setProcessor(Processor processor) {
	this.processor = processor;
}


  
  
}
