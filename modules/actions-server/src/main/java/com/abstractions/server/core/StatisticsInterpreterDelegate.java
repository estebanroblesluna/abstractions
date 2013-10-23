package com.abstractions.server.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.abstractions.api.Message;
import com.abstractions.runtime.interpreter.InterpreterDelegate;
import com.abstractions.template.ElementTemplate;

public class StatisticsInterpreterDelegate implements InterpreterDelegate {

	private Map<String, AtomicLong> receivedMessages;
	private AtomicLong uncaughtExceptions;
	private ReentrantReadWriteLock lock;
	
	public StatisticsInterpreterDelegate() {
		this.uncaughtExceptions = new AtomicLong(0);
		this.lock = new ReentrantReadWriteLock();
		this.receivedMessages = new ConcurrentHashMap<String, AtomicLong>();
	}
	
	public StatisticsInfo reset() {
		try {
			this.lock.writeLock().lock();
			StatisticsInfo info = new StatisticsInfo(this.uncaughtExceptions.get());
			
			this.uncaughtExceptions.set(0);
			
			for (Map.Entry<String, AtomicLong> entry : this.receivedMessages.entrySet()) {
				info.putReceivedMessages(entry.getKey(), entry.getValue().get());
				entry.getValue().set(0);
			}
			
			return info;
		} finally {
			this.lock.writeLock().unlock();
		}
	}
	@Override
	public void startInterpreting(String interpreterId, String threadId,
			String contextId, ElementTemplate currentProcessor,
			Message currentMessage) {
		try {
			this.lock.readLock().lock();
			this.getBucket(currentProcessor.getId()).incrementAndGet();
		} finally {
			this.lock.readLock().unlock();
		}
	}

	private AtomicLong getBucket(String id) {
		if (!this.receivedMessages.containsKey(id)) {
			synchronized (this.receivedMessages) {
				if (!this.receivedMessages.containsKey(id)) {
					this.receivedMessages.put(id, new AtomicLong());
				}
			}
		}
		
		return this.receivedMessages.get(id);
	}

	@Override
	public void uncaughtException(String interpreterId, String threadId,
			String contextId, ElementTemplate currentProcessor,
			Message currentMessage, Exception e) {
		try {
			this.lock.readLock().lock();
			this.uncaughtExceptions.incrementAndGet();
		} finally {
			this.lock.readLock().unlock();
		}
	}
	
	@Override
	public void stopInBreakPoint(String interpreterId, String threadId,
			String contextId, ElementTemplate currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void beforeStep(String interpreterId, String threadId,
			String contextId, ElementTemplate currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void afterStep(String interpreterId, String threadId,
			String contextId, ElementTemplate currentProcessor,
			Message currentMessage) {
	}

	@Override
	public void finishInterpretation(String interpreterId, String threadId,
			String contextId, Message currentMessage) {
	}
}
