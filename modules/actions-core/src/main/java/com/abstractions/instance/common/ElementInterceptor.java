package com.abstractions.instance.common;

import com.abstractions.api.Element;
import com.abstractions.api.Message;

public interface ElementInterceptor {

	void beforeEvaluating(Element element, Message message);
	
	void afterEvaluating(Element element, Message message);
}
