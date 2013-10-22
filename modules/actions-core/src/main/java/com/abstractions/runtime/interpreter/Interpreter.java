package com.abstractions.runtime.interpreter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.Validate;

import com.abstractions.api.Identificable;
import com.abstractions.api.Message;
import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.service.core.ContextDefinition;
import com.abstractions.service.core.ServiceException;
import com.abstractions.utils.IdGenerator;

public class Interpreter implements Identificable, ThreadObserver {

	private static Log log = LogFactory.getLog(Interpreter.class);

	private final String id;
	private final ContextDefinition context;
	private final AtomicLong threadCount;
	private final Map<Long, Thread> threads;
	private final ObjectClazz source;
	private volatile InterpreterDelegate delegate;

	public Interpreter(ContextDefinition context, ObjectClazz source) {
		Validate.notNull(context);
		Validate.notNull(source);
		
		this.context = context;
		this.id = IdGenerator.getNewId();
		this.threadCount = new AtomicLong(0);
		this.threads = new ConcurrentHashMap<Long, Thread>();
		this.source = source;
		this.delegate = new NullInterpreterDelegate();
	}
	
	public Thread run(Message message) {
		log.info("Running thread");
		Thread thread = this.createThread(this.source, message);
		
		this.delegate.startInterpreting(this.id, thread.getId().toString(), this.context.getId(), this.source, message.clone());
		
		thread.run();
		return thread;
	}
	
	public void debug(Message message) {
		log.info("Debugging thread");
		Thread thread = this.createDebuggableThread(this.source, message);
		thread.run();
	}
	
	public String getId() {
		return id;
	}

	ContextDefinition getContext() {
		return context;
	}

	@Override
	public void terminated(Thread thread) {
		this.threads.remove(thread.getId());
	}

	public InterpreterDelegate getDelegate() {
		return delegate;
	}

	public void setDelegate(InterpreterDelegate delegate) {
		this.delegate = delegate;
	}

	public Thread createThread(ObjectClazz source, Message message) {
		Thread thread = new Thread(this, source, message, this.threadCount.incrementAndGet());
		this.threads.put(thread.getId(), thread);
		thread.addObserver(this);
		return thread;
	}

	public Thread createDebuggableThread(ObjectClazz source, Message message) {
		Thread thread = new DebuggableThread(this, source, message, this.threadCount.incrementAndGet());
		this.threads.put(thread.getId(), thread);
		thread.addObserver(this);
		return thread;
	}

	@SuppressWarnings("unchecked")
	public <T extends Thread> T getThread(Long threadId) {
		return (T) this.threads.get(threadId);
	}
	
	ExecutorService getExecutorServiceFor(ObjectClazz definition) {
		return this.context.getExecutorServiceFor(definition);
	}

	public void sync() throws ServiceException {
		this.context.sync();
	}
}
