package com.modules.quartz;

import com.abstractions.api.Message;
import com.abstractions.api.Startable;
import com.abstractions.api.Terminable;
import com.abstractions.instance.messagesource.AbstractMessageSource;

public class JobMessageSource extends AbstractMessageSource implements Startable, Terminable {

	private QuartzConnector connector;
	private String cronExpression;
	
	public JobMessageSource() {
		this.cronExpression = "0 0/1 * * * ?";
	}
	
	public void trigger() {
		this.newMessage(new Message());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void terminate() {
		this.connector.removeJob(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() {
		this.connector.addJob(this);
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
}
