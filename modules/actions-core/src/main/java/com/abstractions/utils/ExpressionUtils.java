package com.abstractions.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.expression.ScriptingExpression;
import com.abstractions.expression.ScriptingLanguage;

public class ExpressionUtils {

  private static final Log log = LogFactory.getLog(ExpressionUtils.class);
  
	@SuppressWarnings("unchecked")
	public static <T> T evaluateNoFail(Expression expression, Message message, T defaultValue) {
		if (expression == null) {
			return defaultValue;
		}
		
		try {
			Object result = expression.evaluate(message);
			return (T) result;
		} catch (Exception e) {
		  log.error("Failed to evaluate expression", e);
			return defaultValue;
		}
	}

	public static Expression groovy(String script) {
		return new ScriptingExpression(ScriptingLanguage.GROOVY, script);
	}
}
