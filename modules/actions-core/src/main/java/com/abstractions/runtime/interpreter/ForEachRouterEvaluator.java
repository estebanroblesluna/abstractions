package com.abstractions.runtime.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.expression.ScriptingExpression;
import com.abstractions.expression.ScriptingLanguage;
import com.abstractions.instance.core.ConnectionType;
import com.abstractions.service.core.BeanUtils;
import com.abstractions.template.ElementTemplate;

public class ForEachRouterEvaluator implements Evaluator {

  private static Log log = LogFactory.getLog(ForEachRouterEvaluator.class);

  @SuppressWarnings("unchecked")
  @Override
  public void evaluate(Thread thread) {
    //message is the same so update the current processor
    ElementTemplate newDefinition = this.getForEachObjectDefinition(thread.getCurrentObjectDefinition(), thread);
    
    if (newDefinition != null) {
      Message currentMessage = thread.getCurrentMessage();
      
      Object payload = currentMessage.getPayload();
      List<Object> list;
      if (!(payload instanceof List)) {
        list = new ArrayList<Object>();
        list.add(payload);
      } else {
        list = (List<Object>)payload;
      }

      String targetId = newDefinition.getProperty("target");
      ElementTemplate targetElement = thread.getComposite().resolve(targetId);

      CountDownLatch latch = new CountDownLatch(list.size());
      ElementTemplate currentElement = thread.getCurrentObjectDefinition();
      ExecutorService service = thread.getExecutorServiceFor(currentElement);

      int index = 0;
      for (Object o : list) {
        Message newMessage = currentMessage.clone();
        newMessage.setPayload(o);
        Expression targetExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, "message.payload[" + index + "] = result.payload");
        thread.startSubthread(service, newMessage, currentMessage, targetElement, targetExpression, latch);
        index++;
      }
      
      try {
        latch.await();
        thread.computeNextInChainProcessorAndSet();
      } catch (InterruptedException e) {
        log.warn("Interrupting thread", e);
      }
    } else {
      thread.computeNextInChainProcessorAndSet();
    }   
  }
  
  private ElementTemplate getForEachObjectDefinition(ElementTemplate choiceRouter, Thread thread) {
    //this is a for each processor so get the connections property
    String connections = choiceRouter.getProperty("__connections" + ConnectionType.FOR_EACH_CONNECTION.getElementName());
    List<String> urns = BeanUtils.getUrnsFromList(connections);
    
    for (String urn : urns) {
      ElementTemplate connectionDefinition = thread.getComposite().resolve(urn);
      if (connectionDefinition != null) {
        return connectionDefinition;
      }
    }
    
    return null;
  }
}
