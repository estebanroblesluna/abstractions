package com.abstractions.service.core;

import com.abstractions.generalization.ApplicationTemplate;
import com.abstractions.template.CompositeTemplate;

public interface ApplicationTransformation {

	void transform(CompositeTemplate context, ApplicationTemplate appTemplate);
}
