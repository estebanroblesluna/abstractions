package com.modules.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.abstractions.api.Message;
import com.abstractions.api.Startable;
import com.abstractions.api.Terminable;

public class HttpMessageSource extends AbstractHttpMessageSource implements Terminable, Startable {

	private static Log log = LogFactory.getLog(HttpMessageSource.class);

	private volatile int port;

	public HttpMessageSource() {
		super();
	}

	public synchronized void start() {
		this.stopCurrentServer();

		try {
			HttpServerHolder.getInstance().start(this.port);
		} catch (Exception e) {
			log.warn("Error starting server", e);
		}
		
		try {
			HttpServerHolder.getInstance().register(this, this.port);
		} catch (Exception e) {
			log.warn("Error registering server", e);
		}
	}

	public synchronized void stop() {
		this.stopCurrentServer();
	}
	
	@Override
	public Message newMessage(Message message) {
		return super.newMessage(message);
	}

	private void stopCurrentServer() {
		HttpServerHolder.getInstance().stop(this.port);
	}
	
	@Override
	public void terminate() {
		HttpServerHolder.getInstance().stop(this.port);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	public String getTestUrl() {
		return HttpServerHolder.getInstance().getTestUrl(this);
	}
}
