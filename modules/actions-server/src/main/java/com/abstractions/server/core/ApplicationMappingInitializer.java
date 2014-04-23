package com.abstractions.server.core;

import java.util.List;

import com.abstractions.meta.ElementDefinition;
import com.abstractions.model.Library;
import com.abstractions.service.core.NamesMapping;

public class ApplicationMappingInitializer {

	public NamesMapping buildFrom(List<ElementDefinition> elements) {
		return this.buildFromInto(elements, new NamesMapping());
	}

	public NamesMapping buildFromInto(List<ElementDefinition> elements, NamesMapping mapping) {
		Library library = new Library("elements");
		
		for (ElementDefinition definition : elements) {
			library.addDefinition(definition);
		}
		
		library.createMappings(mapping);
		
		return mapping;
	}
}
