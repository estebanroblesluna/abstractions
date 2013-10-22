package com.abstractions.utils;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.expression.ScriptingExpression;
import com.abstractions.expression.ScriptingLanguage;

public class ExpressionUtils {

	@SuppressWarnings("unchecked")
	public static <T> T evaluateNoFail(Expression expression, Message message, T defaultValue) {
		if (expression == null) {
			return defaultValue;
		}
		
		try {
			Object result = expression.evaluate(message);
			return (T) result;
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static Expression groovy(String script) {
		return new ScriptingExpression(ScriptingLanguage.GROOVY, script);
	}
}
