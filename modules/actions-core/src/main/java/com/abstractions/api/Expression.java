package com.abstractions.api;

public interface Expression {
	Object evaluate(Message message);

	Object evaluate(Message message, String[] namedArguments,
			Object... arguments);
}
