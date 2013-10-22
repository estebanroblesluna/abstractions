package com.abstractions.expression;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;

public abstract class AbstractExpression implements Expression {

	  public Object evaluate(Message message) {
		  return this.evaluate(message, null);
	  }
}
