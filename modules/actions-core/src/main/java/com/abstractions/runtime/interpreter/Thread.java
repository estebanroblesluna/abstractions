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
import com.abstractions.api.Evaluable;
import com.abstractions.api.Message;
import com.abstractions.api.Processor;
import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.instance.core.AllConnection;
import com.abstractions.service.core.ContextDefinition;

public class Thread {

	private static Log log = LogFactory.getLog(Thread.class);
	
	protected volatile AtomicBoolean hasNextProcessor;
	protected volatile Message currentMessage;
	protected volatile ObjectClazz currentElement;
	protected volatile Interpreter interpreter;
	protected volatile Stack<ThreadContext> threadContexts;
	private volatile List<ThreadObserver> observers;
	
	private final Long id;
	
	public Thread(Interpreter interpreter, ObjectClazz source, Message message, Long id) {
		this.currentElement = source;
		this.hasNextProcessor = new AtomicBoolean(true);
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
	
	public ContextDefinition getContext() {
		return this.threadContexts.peek().getContext();
	}

	public ContextDefinition getRootContext() {
		return this.interpreter.getContext();
	}

	protected void basicStep() {
		ObjectClazz definition = this.currentElement;
		log.info("[Thread" + this.id + "] " + "START Running step for: " + StringUtils.defaultString(definition.getProperty(ObjectClazz.NAME)));

		Element element = this.getCurrentElement();

		if (element instanceof Evaluable) {
			Evaluator evaluator = this.getContext().getMapping().getEvaluators().get(this.currentElement.getElementName());
			evaluator.evaluate(this);
		} else if (element instanceof Processor) {
			this.currentMessage = ((Processor) element).process(this.currentMessage);
			this.currentElement = this.computeNextInChainProcessor();
		}

		this.hasNextProcessor.set(this.currentElement != null);

		log.info("[Thread" + this.id + "] " + "END Running step for: " + StringUtils.defaultString(definition.getProperty(ObjectClazz.NAME))
				+ " HAS NEXT " + this.hasNextProcessor.get());
	}
	
	protected void basicRun() {
		try {
			while (this.hasNext() && !this.stopOnCurrentElement()) {
				ObjectClazz currentDefinition = this.currentElement;
				this.beforeStep(currentDefinition);
				this.basicStep();
				this.afterStep(currentDefinition);
			}
			
			if (this.hasNext()) {
				log.info(
						"[Thread" + this.id + "] " 
								+ "Stop on breakpoint in: " 
								+ StringUtils.defaultString(this.currentElement.getProperty(ObjectClazz.NAME)));
				
				this.interpreter.getDelegate().stopInBreakPoint(
						this.interpreter.getId(), 
						this.getId().toString(), 
						this.getContext().getId(), 
						this.currentElement, 
						this.currentMessage.clone());
			} else {
				log.info("[Thread" + this.id + "] " + "has finished");
				
				this.interpreter.getDelegate().finishInterpretation(
						this.interpreter.getId(), 
						this.getId().toString(), 
						this.getRootContext().getId(), 
						this.currentMessage.clone());
				
				this.notifyObserversOfTermination();
			}
		} catch (Exception e) {
			this.interpreter.getDelegate().uncaughtException(
					this.interpreter.getId(), 
					this.getId().toString(), 
					this.getContext().getId(), 
					this.currentElement, 
					this.currentMessage.clone(),
					e);
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

	protected void afterStep(ObjectClazz currentDefinition) {
	}

	protected void beforeStep(ObjectClazz currentDefinition) {
	}

	protected boolean hasNext() {
		return this.hasNextProcessor.get();
	}

	private ObjectClazz computeNextInChainProcessor() {
		log.info("[Thread" + this.id + "] " +"START Computing next processor of: " + StringUtils.defaultString(this.currentElement.getProperty(ObjectClazz.NAME)));

		ObjectClazz nextInChain = this.getContext().getNextInChainFor(this.currentElement);
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
				nextInChain = this.getContext().getNextInChainFor(this.currentElement);
			}
		}

		log.info("[Thread" + this.id + "] " +"END Computing next processor of: " + StringUtils.defaultString(this.currentElement.getProperty(ObjectClazz.NAME))
				+ " with result " + (nextInChain == null ? "null" : StringUtils.defaultString(nextInChain.getProperty(ObjectClazz.NAME))));

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

	public ObjectClazz getCurrentObjectDefinition() {
		return currentElement;
	}
	
	public Long getId() {
		return this.id;
	}

	void startSubthread(ExecutorService service, final Message newMessage, final Message storeResultMessage, 
			final ObjectClazz allConnectionDefinition, final CountDownLatch latch) {
		
		service.execute(new Runnable() {
			@Override
			public void run() {
				//get the target object definition
				String urnTarget = allConnectionDefinition.getProperty("target");
				ObjectClazz targetDefinition = getContext().resolve(urnTarget);
				
				//create the subthread starting from the target and with the copy of the message
				final Thread subthread = createSubthread(targetDefinition, newMessage);
				
				//when the thread terminates store the result in the previous message and count down the latch
				subthread.addObserver(new ThreadObserver() {
					@Override
					public void terminated(Thread thread) {
						AllConnection allConnection = (AllConnection) allConnectionDefinition.getInstance();
						if (allConnection.getTargetExpression() != null) {
							allConnection.getTargetExpression().evaluate(storeResultMessage, new String[] {"result"}, thread.getCurrentMessage());
						}
						latch.countDown();
					}
				});
				
				//start the subthread
				subthread.run();
			}
		});
	}

	ExecutorService getExecutorServiceFor(ObjectClazz definition) {
		return this.interpreter.getExecutorServiceFor(definition);
	}

	protected Thread createSubthread(ObjectClazz source, Message message) {
		return this.interpreter.createThread(source, message);
	}
	
	public Interpreter getInterpreter() {
		return this.interpreter;
	}

	public void computeNextInChainProcessorAndSet() {
		this.currentElement = this.computeNextInChainProcessor();
	}

	public void setCurrentElement(ObjectClazz element) {
		this.currentElement = element;
	}
	
	public void pushCurrentContext() {
		this.pushCurrentContext(this.getContext());
	}
	
	public void pushCurrentContext(ContextDefinition context) {
		ThreadContext threadContext = new ThreadContext(context, this.currentElement, this.currentMessage);
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
