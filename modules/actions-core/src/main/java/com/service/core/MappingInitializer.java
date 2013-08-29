package com.service.core;

import com.core.meta.Library;
import com.core.meta.Meta;

public class MappingInitializer {

	private NamesMapping mapping;

	public void initializeMapping() {
		Library common = Meta.getCommonLibrary();
		Library modules = Meta.getModulesLibrary();
		
		common.createMappings(this.mapping);
		modules.createMappings(this.mapping);
	}
	
	public NamesMapping getMapping() {
		return mapping;
	}

	public void setMapping(NamesMapping mapping) {
		this.mapping = mapping;
	}
}
