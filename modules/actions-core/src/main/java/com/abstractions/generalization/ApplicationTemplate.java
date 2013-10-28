package com.abstractions.generalization;

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

		message.putProperty(MessageUtils.APPLICATION_ID_PROPERTY, this.getMeta().getId());
		//TODO set all application properties here
		message.putProperty(MessageUtils.APPLICATION_PROPERTY_BASE_PROPERTY + ".cdn", "http://");
		
		Thread root = interpreter.run(message);
		return root.getCurrentMessage();
	}
}
