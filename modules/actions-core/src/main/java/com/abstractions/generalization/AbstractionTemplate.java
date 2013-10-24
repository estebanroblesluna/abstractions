package com.abstractions.generalization;

import com.abstractions.meta.AbstractionDefinition;
import com.abstractions.service.core.NamesMapping;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class AbstractionTemplate extends CompositeTemplate {
	
	private ElementTemplate startingElement;
	
	public AbstractionTemplate(AbstractionDefinition metaElementDefinition, NamesMapping mapping) {
		super(metaElementDefinition, mapping);
	}

	public AbstractionTemplate(String id, AbstractionDefinition metaElementDefinition, NamesMapping mapping) {
		super(id, metaElementDefinition, mapping);
	}

	public ElementTemplate getStartingElement() {
		return startingElement;
	}

	public void setStartingElement(ElementTemplate startingElement) {
		this.startingElement = startingElement;
	}
}
