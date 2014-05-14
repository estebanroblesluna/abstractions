package com.abstractions.service.core;

import com.abstractions.meta.ApplicationDefinition;

public interface ApplicationDefinitionLoader {

	ApplicationDefinition load(long applicationId, NamesMapping mapping);
}
