package com.abstractions.generalization;

import com.abstractions.api.Element;
import com.abstractions.instance.messagesource.MessageSource;
import com.abstractions.instance.messagesource.MessageSourceListener;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class ApplicationTemplate extends CompositeTemplate {
	
	public ApplicationTemplate(ElementDefinition metaElementDefinition, NamesMapping mapping) {
		super(metaElementDefinition, mapping);
	}

	public ApplicationTemplate(String id, ElementDefinition metaElementDefinition, NamesMapping mapping) {
		super(id, metaElementDefinition, mapping);
	}
	
	protected void afterInstantiation(Element object, ElementTemplate definition) {
		if (object instanceof MessageSource) {
			((MessageSource) object).setMainListener((MessageSourceListener) this.metaElementDefinition);
		}
	}
	
	protected void afterScan(Element object, ElementTemplate definition) {
		if (object instanceof MessageSource) {
			((MessageSource) object).setMainListener((MessageSourceListener) this.metaElementDefinition);
		}
	}
}
