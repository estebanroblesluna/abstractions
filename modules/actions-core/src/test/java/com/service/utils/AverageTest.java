package com.service.utils;

import junit.framework.TestCase;

import com.core.utils.Average;

public class AverageTest extends TestCase {

	public void testAverage() {
		Average average = new Average();
		double total = 0;
		
		for (int i = 0; i < 10000; i++) {
			double value = Math.random() * 10d;
			average.append(value);
			
			total = total + value;
			double computedAverage = total / (i + 1);
			
			assertTrue(Math.abs(average.average() - computedAverage) < 0.0001);
		}
	}
}
