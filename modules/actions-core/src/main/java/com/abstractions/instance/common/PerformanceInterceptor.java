package com.abstractions.instance.common;

import java.util.concurrent.atomic.AtomicLong;

import com.abstractions.api.Element;
import com.abstractions.api.Message;
import com.abstractions.utils.Average;

public class PerformanceInterceptor implements ElementInterceptor {

	private static final AtomicLong performanceIds = new AtomicLong(0);
	
	private final Average average;
	private final long id;
	private final String key;
	
	public PerformanceInterceptor() {
		this.average = new Average();
		this.id = performanceIds.incrementAndGet();
		this.key = "__performance_before_#" + this.id;
	}

	@Override
	public void beforeEvaluating(Element element, Message message) {
		long before = System.currentTimeMillis();
		message.putProperty(this.key, before);
	}

	@Override
	public void afterEvaluating(Element element, Message message) {
		Object beforeObject = message.getProperty(this.key);
		if (beforeObject != null && beforeObject instanceof Long) {
			long before = (Long) beforeObject;
			long after = System.currentTimeMillis();
			this.average.append(after - before);
		}
	}
	
	public double getAverage() {
		return this.average.average();
	}
}
