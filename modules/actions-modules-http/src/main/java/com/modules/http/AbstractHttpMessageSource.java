package com.modules.http;

import com.abstractions.api.ApplicationAware;
import com.abstractions.api.Expression;
import com.abstractions.instance.messagesource.AbstractMessageSource;

public abstract class AbstractHttpMessageSource extends AbstractMessageSource implements ApplicationAware {

	private volatile Expression timeoutExpression;
	private volatile String applicationId;

	public Expression getTimeoutExpression() {
		return timeoutExpression;
	}

	public void setTimeoutExpression(Expression timeoutExpression) {
		this.timeoutExpression = timeoutExpression;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
}
