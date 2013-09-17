package com.service.core;

import com.abstractions.model.Library;
import com.core.meta.Meta;

public class BasicMappingInitializer {

	private NamesMapping mapping;

	public void basicSetupInitialLibraries() {
		Library common = Meta.getCommonLibrary();
		Library modules = Meta.getModulesLibrary();
		
		common.createMappings(mapping);
		modules.createMappings(mapping);
	}

	public NamesMapping getMapping() {
		return mapping;
	}

	public void setMapping(NamesMapping mapping) {
		this.mapping = mapping;
	}
}
