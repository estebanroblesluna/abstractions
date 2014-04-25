package org.modules.jetty;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyHttpServer {
	private Server server;
	private ServletContextHandler handler;

	public JettyHttpServer(int port) {
		this.server = new Server(port);
		this.handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		this.handler.setContextPath("/");
		this.server.setHandler(this.handler);
	}

	public void start() throws Exception {
		this.server.start();
	}

	public void stop() throws Exception {
		this.server.stop();
	}

	public ServletHolder addServlet(Class<? extends Servlet> servlet, String pathSpec) {
		return this.handler.addServlet(servlet, pathSpec);
	}

	public boolean isRunning() {
		return this.server.isRunning();
	}
}
