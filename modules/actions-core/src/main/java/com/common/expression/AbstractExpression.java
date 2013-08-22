package com.common.expression;

import com.core.api.Expression;
import com.core.api.Message;

public abstract class AbstractExpression implements Expression {

	  public Object evaluate(Message message) {
		  return this.evaluate(message, null);
	  }
}
