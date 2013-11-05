package com.abstractions.instance.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.StringUtils;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.api.Processor;
import com.abstractions.expression.ScriptingExpression;
import com.abstractions.expression.ScriptingLanguage;
import com.abstractions.utils.ExpressionUtils;

public class LogProcessorWrapper extends ProcessorWrapper {

	private Expression beforeExpression;
	private Expression afterExpression;
	private Expression beforeConditionExpression;
	private Expression afterConditionExpression;
	private String beforeExpressionAsString;
	private String afterExpressionAsString;

	private final ConcurrentLinkedQueue<String> logLines;
	private final AtomicLong linesSize;
	private final int maxLines = 40;
	
	public LogProcessorWrapper(Processor processor) {
		super(processor);
		this.logLines = new ConcurrentLinkedQueue<String>();
		this.linesSize = new AtomicLong(0);
	}
	
	@Override
	public Message process(Message message) {
		boolean log = true;
		try {

			log = false;
			if (this.beforeConditionExpression != null) {
				log = ExpressionUtils.evaluateNoFail(this.beforeConditionExpression, message, false);
			} else {
				log = true;
			}

			if (log && this.beforeExpression != null) {
				Object beforeValue = this.beforeExpression.evaluate(message);
				this.append("[INFO] " + beforeValue);
			}
		} catch (Exception e) {
			this.append("[WARN] Error evaluating BEFORE expression: " + this.beforeExpressionAsString);
		}
		
		message = super.process(message);

		try {
			
			log = false;
			if (this.afterConditionExpression != null) {
				log = ExpressionUtils.evaluateNoFail(this.afterConditionExpression, message, false);
			} else {
				log = true;
			}

			if (log && this.afterExpression != null) {
				Object afterValue = this.afterExpression.evaluate(message);
				this.append("[INFO] " + afterValue);
			}
		} catch (Exception e) {
			this.append("[WARN] Error evaluating AFTER expression: " + this.afterExpressionAsString);
		}

		return message;
	}

	private void append(String line) {
		synchronized (this.linesSize) {
			while (this.linesSize.get() > this.maxLines) {
				this.linesSize.decrementAndGet();
				this.logLines.poll();
			}

			this.linesSize.incrementAndGet();
			this.logLines.add(line);
		}
	}

	public Expression getBeforeExpression() {
		return beforeExpression;
	}

	public Expression getAfterExpression() {
		return afterExpression;
	}
	
	public void setBeforeExpression(String beforeExpression) {
		this.beforeExpressionAsString = beforeExpression;
		if (!StringUtils.isBlank(beforeExpression)) {
			this.beforeExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, beforeExpression);
		}
	}

	public void setBeforeConditionExpression(String expression) {
		this.beforeConditionExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, expression);
	}

	public void setAfterExpression(String afterExpression) {
		this.afterExpressionAsString = afterExpression;
		if (!StringUtils.isBlank(afterExpression)) {
			this.afterExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, afterExpression);
		}
	}

	public void setAfterConditionExpression(String expression) {
		this.afterConditionExpression = new ScriptingExpression(ScriptingLanguage.GROOVY, expression);
	}

	public List<String> lines() {
		return new ArrayList<String>(this.logLines);
	}
}
