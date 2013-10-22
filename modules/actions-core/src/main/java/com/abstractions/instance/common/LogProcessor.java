package com.abstractions.instance.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.core.api.Expression;
import com.core.api.Message;
import com.core.api.Processor;

public class LogProcessor implements Processor {

	private String logName;
	private Expression toLogExpression;
	private Log log;
	
	@Override
	public Message process(Message message) {
		Object value = this.toLogExpression.evaluate(message);
		
		this.getLog().info(value);
		
		return message;
	}

	public Log getLog() {
		if (this.log == null) {
			this.log = LogFactory.getLog(LogProcessor.class);
		}
		return this.log;
	}
	
	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
		log = LogFactory.getLog(this.logName);
	}

	public Expression getToLogExpression() {
		return toLogExpression;
	}

	public void setToLogExpression(Expression toLogExpression) {
		this.toLogExpression = toLogExpression;
	}
}
