package com.abstractions.runtime.interpreter;

import com.abstractions.api.Message;
import com.abstractions.template.ElementTemplate;


public class DevInterpreterDelegate implements InterpreterDelegate {

  private static final DevInterpreterDelegate INSTANCE = new DevInterpreterDelegate();
  
  public static DevInterpreterDelegate getInstance() {
    return INSTANCE;
  }
  
  @Override
  public void startInterpreting(String interpreterId, String threadId, String contextId, ElementTemplate currentProcessor, Message currentMessage) {
    // NOTHING TO DO
  }

  @Override
  public void stopInBreakPoint(String interpreterId, String threadId, String contextId, ElementTemplate currentProcessor, Message currentMessage) {
    // NOTHING TO DO
  }

  @Override
  public void beforeStep(String interpreterId, String threadId, String contextId, ElementTemplate currentProcessor, Message currentMessage) {
    // NOTHING TO DO
  }

  @Override
  public void afterStep(String interpreterId, String threadId, String contextId, ElementTemplate currentProcessor, Message currentMessage) {
    // NOTHING TO DO
  }

  @Override
  public void finishInterpretation(String interpreterId, String threadId, String contextId, Message currentMessage) {
    // NOTHING TO DO
  }

  @Override
  public void uncaughtException(String interpreterId, String threadId, String contextId, ElementTemplate currentProcessor, Message currentMessage, Exception e) {
    currentMessage.setException(e);
  }
}
