package com.modules.http;

import com.abstractions.api.Message;
import com.abstractions.api.Startable;
import com.abstractions.api.Terminable;

public class HttpMessageSource extends AbstractHttpMessageSource implements Terminable, Startable {

	private volatile int port;

	public HttpMessageSource() {
		super();
	}

	public synchronized void start() {
		this.stopCurrentServer();

		HttpServerHolder.getInstance().start(this.port);
		HttpServerHolder.getInstance().register(this, this.port);
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
