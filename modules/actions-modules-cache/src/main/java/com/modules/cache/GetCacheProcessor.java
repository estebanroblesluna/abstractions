package com.modules.cache;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.Processor;

public class GetCacheProcessor implements Processor {
	
	private volatile Cache cache;
	private volatile Expression expression;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Message process(Message message) {
		String key = this.expression.evaluate(message).toString();
		Object result = this.getCache().get(key);
		message.setPayload(result);
		return message;
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
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

	public void setCache(Cache cache) {
		this.cache = cache;
	}
}
