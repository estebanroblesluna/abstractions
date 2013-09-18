package com.core.utils;

import java.util.concurrent.atomic.AtomicLong;

import com.google.common.util.concurrent.AtomicDouble;

public class Average {

	private final AtomicDouble averageSoFar;
	private final AtomicLong count;

	public Average() {
		this.averageSoFar = new AtomicDouble(0);
		this.count = new AtomicLong(0);
	}
	
	public double average() {
		return this.averageSoFar.get();
	}
	
	public synchronized void append(double value) {
		long newCount = this.count.incrementAndGet();
		double plus = value / newCount;
		double before = this.averageSoFar.get() * ((newCount - 1d) / newCount);
		this.averageSoFar.set(before + plus);
	}
}
