package com.abstractions.runtime.interpreter;

import com.abstractions.api.Message;
import com.abstractions.clazz.core.ObjectClazz;
import com.abstractions.expression.ScriptingExpression;
import com.abstractions.expression.ScriptingLanguage;

public class DebuggableThread extends Thread {

	private volatile boolean stopOnBreakpoints;
	private volatile long delay;

	public DebuggableThread(Interpreter interpreter, ObjectClazz source, Message message, Long id) {
		super(interpreter, source, message, id);

		this.stopOnBreakpoints = true;
		this.delay = 500;
	}

	public void run() {
		this.stopOnBreakpoints = true;
		this.basicRun();
	}
	
	protected Thread createSubthread(ObjectClazz source, Message message) {
		return this.interpreter.createDebuggableThread(source, message);
	}
	
	public void resume() {
		this.basicStep();
		this.basicRun();
	}
	
	public void step() {
		this.basicStep();
	}
	
	protected void afterStep(ObjectClazz currentDefinition) {
		this.delayIfNecessary();
		this.interpreter.getDelegate().afterStep(
				this.interpreter.getId(),
				this.getId().toString(), 
				this.getContext().getId(), 
				currentDefinition, 
				this.currentMessage.clone());
	}

	protected void beforeStep(ObjectClazz currentDefinition) {
		this.delayIfNecessary();
		this.interpreter.getDelegate().beforeStep(
				this.interpreter.getId(),
				this.getId().toString(), 
				this.getContext().getId(), 
				currentDefinition, 
				this.currentMessage.clone());
		this.delayIfNecessary();
	}
	
	
	private void delayIfNecessary() {
		if (this.delay > 0) {
			try {
				java.lang.Thread.sleep(this.delay);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public boolean isStopOnBreakpoints() {
		return stopOnBreakpoints;
	}

	public void setStopOnBreakpoints(boolean stopOnBreakpoints) {
		this.stopOnBreakpoints = stopOnBreakpoints;
	}
	
	protected boolean stopOnCurrentElement() {
		return this.stopOnBreakpoints && this.currentElement.hasBreakpoint();
	}
	
	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public Object evaluate(String anExpression) {
		ScriptingExpression expression = new ScriptingExpression(ScriptingLanguage.GROOVY, anExpression);
		Object result = null;
		
		synchronized (this.currentMessage) {
			result = expression.evaluate(this.currentMessage);
		}
		
		return result;
	}
}
