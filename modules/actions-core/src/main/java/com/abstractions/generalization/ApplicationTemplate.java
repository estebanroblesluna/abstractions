package com.abstractions.generalization;

import java.net.MalformedURLException;
import java.net.URL;

import com.abstractions.api.Element;
import com.abstractions.api.Message;
import com.abstractions.instance.messagesource.MessageSource;
import com.abstractions.instance.messagesource.MessageSourceListener;
import com.abstractions.meta.ApplicationDefinition;
import com.abstractions.runtime.interpreter.Interpreter;
import com.abstractions.runtime.interpreter.Thread;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;
import com.abstractions.utils.ApplicationContextHolder;
import com.abstractions.utils.LiquidMlApplicationContext;
import com.abstractions.utils.MessageUtils;

public class ApplicationTemplate extends CompositeTemplate implements MessageSourceListener {
	
	public ApplicationTemplate(ApplicationDefinition metaElementDefinition, NamesMapping mapping) {
		super(metaElementDefinition, mapping);
	}

	public ApplicationTemplate(String id, ApplicationDefinition metaElementDefinition, NamesMapping mapping) {
		super(id, metaElementDefinition, mapping);
	}
	
	public void afterInstantiation(Element object, ElementTemplate definition) {
		if (object instanceof MessageSource) {
			((MessageSource) object).setMainListener(this);
		}
	}
	
	public void afterScan(Element object, ElementTemplate definition) {
		if (object instanceof MessageSource) {
			((MessageSource) object).setMainListener(this);
		}
	}
	
	@Override
	public Message onMessageReceived(MessageSource messageSource, Message message) {
		ElementTemplate nextInChain = this.getNextInChainFor(messageSource.getId());
		Interpreter interpreter = new Interpreter(this, nextInChain);
		
		ApplicationDefinition appDefinition = (ApplicationDefinition) this.getMeta();
		if (appDefinition.getInterpreterDelegate() != null) {
			interpreter.setDelegate(appDefinition.getInterpreterDelegate());
		}
		
    // TODO replace by real app id
		long applicationId = 2;
		
		message.putProperty(MessageUtils.APPLICATION_ID_PROPERTY, applicationId);
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
}
