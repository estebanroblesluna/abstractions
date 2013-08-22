package com.common.expression;

import com.core.api.Message;

public class ConstantExpression extends AbstractExpression
{
  private Object value;

  public ConstantExpression()
  {
	  this(null);
  }
  
  public ConstantExpression(Object value)
  {
    this.value = value;
  }
  
  /**
   * {@inheritDoc}
   */
	@Override
	public Object evaluate(Message message, String[] namedArguments, Object... arguments) {
		return this.value;
	}
}
