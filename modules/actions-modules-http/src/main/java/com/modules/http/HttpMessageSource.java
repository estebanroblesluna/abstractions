package com.modules.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.servlet.ServletHolder;
import org.modules.jetty.JettyHttpServer;

import com.abstractions.api.Expression;
import com.abstractions.api.Message;
import com.abstractions.api.Startable;
import com.abstractions.api.Terminable;
import com.abstractions.instance.messagesource.AbstractMessageSource;

public class HttpMessageSource extends AbstractMessageSource implements Terminable, Startable {

	private static Log log = LogFactory.getLog(HttpMessageSource.class);

	private volatile Expression timeoutExpression;
	private volatile int port;
	private volatile JettyHttpServer server;

	public HttpMessageSource() {
		super();
	}

	public synchronized void start() {
		this.stopCurrentServer();

		this.server = new JettyHttpServer(this.port);
		ServletHolder holder = this.server.addServlet(HttpReceiver.class, "/");
		try {
			holder.start();
			HttpReceiver receiver = (HttpReceiver) holder.getServlet();
			receiver.setMessageSource(this);
			this.server.start();
		} catch (Exception e) {
			log.error("Error starting http server", e);
		}
	}

	public synchronized void stop() {
		this.stopCurrentServer();
	}
	
	@Override
	protected Message newMessage(Message message) {
		return super.newMessage(message);
	}

	private void stopCurrentServer() {
		if (this.server != null) {
			try {
				this.server.stop();
			} catch (Exception e) {
				log.error("Error stopping http server", e);
			}
		}
	}

	public Expression getTimeoutExpression() {
		return timeoutExpression;
	}

	public void setTimeoutExpression(Expression timeoutExpression) {
		this.timeoutExpression = timeoutExpression;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public void terminate() {
		this.stopCurrentServer();
	}
}
