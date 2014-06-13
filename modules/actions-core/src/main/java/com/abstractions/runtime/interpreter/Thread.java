package com.abstractions.runtime.interpreter;

import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Element;
import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.template.CompositeTemplate;
import com.abstractions.template.ElementTemplate;

public class Thread {

	private static Log log = LogFactory.getLog(Thread.class);
	
	protected volatile AtomicBoolean hasNextProcessor;
	protected volatile AtomicBoolean hasException;
	protected volatile Message currentMessage;
	protected volatile ElementTemplate currentElement;
	protected volatile Interpreter interpreter;
	protected volatile Stack<ThreadContext> threadContexts;
	private volatile List<ThreadObserver> observers;
	
	private final Long id;
	
	public Thread(Interpreter interpreter, ElementTemplate source, Message message, Long id) {
		this.currentElement = source;
		this.hasNextProcessor = new AtomicBoolean(true);
		this.hasException = new AtomicBoolean(false);
		this.interpreter = interpreter;
		this.currentMessage = message;
		this.id = id;
		this.threadContexts = new Stack<ThreadContext>();
		this.observers = new CopyOnWriteArrayList<ThreadObserver>();
		
		ThreadContext rootContext = new ThreadContext(interpreter.getContext(), source, message);
		this.threadContexts.push(rootContext);
	}
	
	public void run() {
		this.basicRun();
	}
	
	public Evaluator getEvaluator(String name) {
		return this.getComposite().getMapping().getEvaluators().get(name);
	}
	
	public CompositeTemplate getComposite() {
		return this.threadContexts.peek().getComposite();
	}

	public CompositeTemplate getRootContext() {
		return this.interpreter.getContext();
	}

	protected void basicStep() {
		try {
			ElementTemplate definition = this.currentElement;
			log.info("[Thread" + this.id + "] " + "START Running step for: " + StringUtils.defaultString(definition.getProperty(ElementTemplate.NAME)));
	
			this.currentElement.evaluateUsing(this);
			this.hasNextProcessor.set(this.currentElement != null);
	
			log.info("[Thread" + this.id + "] " + "END Running step for: " + StringUtils.defaultString(definition.getProperty(ElementTemplate.NAME))
					+ " HAS NEXT " + this.hasNextProcessor.get());

		} catch (Exception e) {
			this.interpreter.getDelegate().uncaughtException(
					this.interpreter.getId(), 
					this.getId().toString(), 
					this.getComposite().getId(), 
					this.currentElement, 
					this.currentMessage,
					e);
			this.hasNextProcessor.set(false);
			this.hasException.set(true);
		}
	}
	
	protected void basicRun() {
		while (this.hasNext() && !this.stopOnCurrentElement()) {
			ElementTemplate currentDefinition = this.currentElement;
			this.beforeStep(currentDefinition);
			this.basicStep();
			this.afterStep(currentDefinition);
		}
		
		this.notifyStopped();
	}

	public void notifyStopped() {
		if (this.hasNext()) {
			log.info(
					"[Thread" + this.id + "] " 
							+ "Stop on breakpoint in: " 
							+ StringUtils.defaultString(this.currentElement.getProperty(ElementTemplate.NAME)));
			
			this.interpreter.getDelegate().stopInBreakPoint(
					this.interpreter.getId(), 
					this.getId().toString(), 
					this.getComposite().getId(), 
					this.currentElement, 
					this.currentMessage.clone());
		} else {
			log.info("[Thread" + this.id + "] " + "has finished");
			
			if (!this.hasException.get()) {
				this.interpreter.getDelegate().finishInterpretation(
						this.interpreter.getId(), 
						this.getId().toString(), 
						this.getRootContext().getId(), 
						this.currentMessage.clone());
			}

			this.notifyObserversOfTermination();
		}
	}
	
	protected boolean stopOnCurrentElement() {
		return false;
	}
	
	private synchronized void notifyObserversOfTermination() {
		this.notifyAll();

		for (ThreadObserver observer : this.observers) {
			observer.terminated(this);
		}
	}

	protected void afterStep(ElementTemplate currentDefinition) {
	}

	protected void beforeStep(ElementTemplate currentDefinition) {
	}

	protected boolean hasNext() {
		return this.hasNextProcessor.get();
	}

	private ElementTemplate computeNextInChainProcessor() {
		log.info("[Thread" + this.id + "] " +"START Computing next processor of: " + StringUtils.defaultString(this.currentElement.getProperty(ElementTemplate.NAME)));

		ElementTemplate nextInChain = this.getComposite().getNextInChainFor(this.currentElement);
		while (nextInChain == null && !this.threadContexts.isEmpty()) {
			log.info("[Thread" + this.id + "] " +"Seems that we need to pop up from the context stack");
			//save the message to be overriden
			Message messageResult = this.currentMessage;
			this.popCurrentContext();
			//override message
			this.currentMessage = messageResult;
			
			//compute next in chain again
			if (this.threadContexts.isEmpty()) {
				//we have reached the root context
				nextInChain = null;
			} else {
				nextInChain = this.getComposite().getNextInChainFor(this.currentElement);
			}
		}

		log.info("[Thread" + this.id + "] " +"END Computing next processor of: " + StringUtils.defaultString(this.currentElement.getProperty(ElementTemplate.NAME))
				+ " with result " + (nextInChain == null ? "null" : StringUtils.defaultString(nextInChain.getProperty(ElementTemplate.NAME))));

		return nextInChain;
	}

	public boolean hasFinished() {
		return this.currentElement == null;
	}
	
	public Message getCurrentMessage() {
		return currentMessage;
	}

	public Element getCurrentElement() {
		return (Element) currentElement.getInstance();
	}

	public ElementTemplate getCurrentObjectDefinition() {
		return currentElement;
	}
	
	public Long getId() {
		return this.id;
	}

	void startSubthread(
	        ExecutorService service, 
	        final Message newMessage, 
	        final Message storeResultMessage, 
	        final ElementTemplate targetDefinition,
	        final Expression targetExpression,
	        final CountDownLatch latch) {
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				//create the subthread starting from the target and with the copy of the message
				final Thread subthread = createSubthread(targetDefinition, newMessage);
				
				//when the thread terminates store the result in the previous message and count down the latch
				subthread.addObserver(new ThreadObserver() {
					@Override
					public void terminated(Thread thread) {
						if (targetExpression != null) {
						  targetExpression.evaluate(storeResultMessage, new String[] {"result"}, thread.getCurrentMessage());
						}
						latch.countDown();
					}
				});
				
				//start the subthread
				subthread.run();
			}
		});
	}

	ExecutorService getExecutorServiceFor(ElementTemplate definition) {
		return this.interpreter.getExecutorServiceFor(definition);
	}

	protected Thread createSubthread(ElementTemplate source, Message message) {
		return this.interpreter.createThread(source, message);
	}
	
	public Interpreter getInterpreter() {
		return this.interpreter;
	}

	public void computeNextInChainProcessorAndSet() {
		this.currentElement = this.computeNextInChainProcessor();
	}

	public void setCurrentElement(ElementTemplate element) {
		this.currentElement = element;
	}
	
	public void setCurrentMessage(Message message) {
		this.currentMessage = message;
	}
	
	public void pushCurrentContext() {
		this.pushCurrentContext(this.getComposite());
	}
	
	public void pushCurrentContext(CompositeTemplate composite) {
		ThreadContext threadContext = new ThreadContext(composite, this.currentElement, this.currentMessage);
		synchronized (this.threadContexts) {
			this.threadContexts.push(threadContext);
		}
	}

	void popCurrentContext() {
		synchronized (this.threadContexts) {
			ThreadContext context = this.threadContexts.pop();
			//this.currentMessage = context.getMessage();
			this.currentElement = context.getProcessor();
		}
	}
	
	public void addObserver(ThreadObserver observer) {
		this.observers.add(observer);
	}

	public synchronized void awaitTermination() throws InterruptedException {
		this.wait();
	}
}
