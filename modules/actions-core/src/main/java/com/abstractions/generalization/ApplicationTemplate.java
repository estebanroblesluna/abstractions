package com.abstractions.generalization;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.abstractions.api.Element;
import com.abstractions.api.Message;
import com.abstractions.instance.messagesource.MessageSource;
import com.abstractions.instance.messagesource.MessageSourceListener;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.meta.FlowDefinition;
import com.abstractions.runtime.interpreter.Interpreter;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.PropertiesLoader;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;
import com.abstractions.utils.LiquidMlApplicationContext;
import com.abstractions.utils.MessageUtils;

public class ApplicationTemplate extends CompositeTemplate implements MessageSourceListener {
	
  private final PropertiesLoader propertiesLoader;
  
	public ApplicationTemplate(ApplicationDefinition metaElementDefinition, NamesMapping mapping, PropertiesLoader propertiesLoader) {
		super(metaElementDefinition, mapping);
		
		this.propertiesLoader = propertiesLoader;
	}

	public ApplicationTemplate(String id, ApplicationDefinition metaElementDefinition, NamesMapping mapping, PropertiesLoader propertiesLoader) {
		super(id, metaElementDefinition, mapping);

	   this.propertiesLoader = propertiesLoader;
	}

  public synchronized Element sync() throws ServiceException {
    return this.sync(this.getMapping());
  }

  public synchronized Element sync(NamesMapping mapping) throws ServiceException {
    if (mapping == null) {
      mapping = this.getMapping();
    }
    
    if (this.object == null) {
      this.instantiate(null, mapping, this);
    }

    this.initialize(null, mapping, this);

    return this.object;
  }
	 
	@Override
	public Message onMessageReceived(MessageSource messageSource, Message message) {
		long applicationId = this.getMeta().getId();
		message.putProperty(MessageUtils.APPLICATION_ID_PROPERTY, applicationId);
		
		if (this.propertiesLoader != null) {
	    Map<String, String> properties = this.propertiesLoader.loadPropertiesOf(applicationId);
	    
	    for (Map.Entry<String, String> entry : properties.entrySet()) {
	      message.putProperty(MessageUtils.PROPERTY_BASE + entry.getKey(), entry.getValue());
	    }
		}
		
		ElementTemplate nextInChain = this.getNextInChainFor(messageSource.getId());
		Interpreter interpreter = new Interpreter(this, nextInChain, this);
		
		ApplicationDefinition appDefinition = (ApplicationDefinition) this.getMeta();
		if (appDefinition.getInterpreterDelegate() != null) {
			interpreter.setDelegate(appDefinition.getInterpreterDelegate());
		}
		
		try {
		  // TODO define actions.http.requestURL as a constant 
      URL requestUrl = new URL((String) message.getProperty("actions.http.requestURL"));
      String cdnUrl = 
              requestUrl.getProtocol() + "://" + 
              requestUrl.getHost() + ":" + 
              LiquidMlApplicationContext.getInstance().getLocalCdnPort() + 
              LiquidMlApplicationContext.getInstance().getLocalCdnBaseUrlFor(applicationId);
      message.putProperty(MessageUtils.APPLICATION_CDN_PROPERTY, cdnUrl);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
		
		Thread root = interpreter.run(message);
		return root.getCurrentMessage();
	}

  public CompositeTemplate createFlow() {
    NamesMapping mapping = ((ApplicationDefinition)this.getMeta()).getMapping();
    ElementDefinition definition = new FlowDefinition();
    CompositeTemplate newFlow = new CompositeTemplate(definition, mapping);
    this.addDefinition(newFlow);
    return newFlow;
  }
  
  public CompositeTemplate getFlow(String flowId) {
    ElementTemplate element = this.getDefinition(flowId);
    if (element == null) {
      return null;
    } else {
      if (element instanceof CompositeTemplate) {
        return (CompositeTemplate) element;
      } else {
        throw new RuntimeException(flowId + " is not a flow");
      }
    }
  }

}
