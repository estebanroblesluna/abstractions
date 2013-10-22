package com.modules.cache;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.api.Processor;

public class PutCacheProcessor implements Processor {

	private volatile Cache cache;
	private Expression keyExpression;
	private Expression valueExpression;
	private boolean replacePayload = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message process(Message message) {
		String key = this.keyExpression.evaluate(message).toString();
		Object value = this.valueExpression.evaluate(message);
		Object oldValue = this.getCache().put(key, value);

		if (this.replacePayload) {
			message.setPayload(oldValue);
		}

		return message;
	}

	public Expression getKeyExpression() {
		return keyExpression;
	}

	public void setKeyExpression(Expression keyExpression) {
		this.keyExpression = keyExpression;
	}

	public Expression getValueExpression() {
		return valueExpression;
	}

	public void setValueExpression(Expression valueExpression) {
		this.valueExpression = valueExpression;
	}

	private Cache getCache() {
		if (this.cache == null) {
			synchronized (this) {
				if (this.cache == null) {
					this.cache = new MemcachedCache();
				}
			}
		}
		return cache;
	}

}
