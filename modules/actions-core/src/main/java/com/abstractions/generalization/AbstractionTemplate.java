package com.abstractions.generalization;

import com.abstractions.api.CompositeElement;
import com.abstractions.api.Element;
import com.abstractions.meta.ElementDefinition;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.service.core.ServiceException;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class AbstractionTemplate extends CompositeTemplate {
	
	private ElementTemplate startingElement;
	
	public AbstractionTemplate(ElementDefinition metaElementDefinition, NamesMapping mapping) {
		super(metaElementDefinition, mapping);
	}

	public AbstractionTemplate(String id, ElementDefinition metaElementDefinition, NamesMapping mapping) {
		super(id, metaElementDefinition, mapping);
	}

	public synchronized Element instantiate(CompositeElement context, NamesMapping mapping) throws ServiceException {
		return this.basicInstantiate(context, mapping);
	}
	
	public ElementTemplate getStartingElement() {
		return startingElement;
	}

	public void setStartingElement(ElementTemplate startingElement) {
		this.startingElement = startingElement;
	}
}
