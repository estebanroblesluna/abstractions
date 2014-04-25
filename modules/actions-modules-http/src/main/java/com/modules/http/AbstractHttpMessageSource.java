package com.modules.http;

import com.abstractions.api.Expression;
import com.abstractions.instance.messagesource.AbstractMessageSource;

public abstract class AbstractHttpMessageSource extends AbstractMessageSource {

	private volatile Expression timeoutExpression;

	public Expression getTimeoutExpression() {
		return timeoutExpression;
	}

	public void setTimeoutExpression(Expression timeoutExpression) {
		this.timeoutExpression = timeoutExpression;
	}
}
