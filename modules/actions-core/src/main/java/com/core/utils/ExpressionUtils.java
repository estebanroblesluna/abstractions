package com.core.utils;

import com.common.expression.ScriptingExpression;
import com.common.expression.ScriptingLanguage;
import com.core.api.Expression;
import com.core.api.Message;

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
