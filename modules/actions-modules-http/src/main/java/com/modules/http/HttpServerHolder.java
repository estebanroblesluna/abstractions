package com.modules.http;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.servlet.ServletHolder;
import org.modules.jetty.JettyHttpServer;

import com.abstractions.model.Environment;

public class HttpServerHolder {

	private static Integer DEV_PORT = 9090;
	private static Environment ENVIRONMENT = Environment.DEV;
	
	private static Log log = LogFactory.getLog(HttpServerHolder.class);
	private static HttpServerHolder INSTANCE;
	
	public static Boolean initialize(Integer devPort, Environment env) {
		DEV_PORT = devPort;
		ENVIRONMENT = env;
		return true;
	}
	
	public static HttpServerHolder getInstance() {
		if (INSTANCE == null) {
			synchronized (HttpServerHolder.class) {
				if (INSTANCE == null) {
					INSTANCE = new HttpServerHolder();
				}
			}
		}
		
		return INSTANCE;
	}
	
	private final Map<Integer, JettyHttpServer> servers;
	
	private HttpServerHolder() {
		this.servers = new ConcurrentHashMap<Integer, JettyHttpServer>();
	}
	
	public void start(Integer port) {
		try {
			this.getServer(port).start();
		} catch (Exception e) {
			log.warn("Error starting http server on port " + port, e);
		}
	}
	
	public void register(HttpMessageSource messageSource, Integer port) {
		JettyHttpServer server = this.getServer(port); //make sure the receiver is created
		
		if (this.isDev()) {
			AbstractHttpMessageSource ms = (AbstractHttpMessageSource) server.getHandler().getAttribute(HttpReceiver.MESSAGE_SOURCE);
			if (ms == null) {
				ms = new DevHttpMessageSource();
				server.getHandler().setAttribute(HttpReceiver.MESSAGE_SOURCE, ms);
			}
			
			DevHttpMessageSource devMessageSource = (DevHttpMessageSource) ms;
			devMessageSource.addSource(messageSource);
		} else {
			server.getHandler().setAttribute(HttpReceiver.MESSAGE_SOURCE, messageSource);
		}
	}

	public void stop(Integer port) {
		try {
			this.getServer(port).stop();
		} catch (Exception e) {
			log.warn("Error stopping http server on port " + port, e);
		}
	}

	public boolean isRunning(Integer port) {
		return this.getServer(port).isRunning();
	}
	
	private JettyHttpServer getServer(Integer port) {
		if (!this.servers.containsKey(port)) {
			synchronized (this.servers) {
				if (!this.servers.containsKey(port)) {
					if (this.isDev()) {
						if (!port.equals(DEV_PORT)) {
							JettyHttpServer server = this.getServer(DEV_PORT);
							this.servers.put(port, server);
						} else {
							return this.buildServerAndReceiver(port); //DEV PORT case
						}
					} else {
						return this.buildServerAndReceiver(port); //OTHER ENV case
					}
				}
			}
		}
		
		return this.servers.get(port);
	}
	
	private JettyHttpServer buildServerAndReceiver(Integer port) {
		JettyHttpServer server = new JettyHttpServer(port);
		ServletHolder holder = server.addServlet(HttpReceiver.class, "/");
		try {
			holder.start();
		} catch (Exception e) {
			throw new RuntimeException("Can't start servlet", e);
		}
		
		this.servers.put(port, server);
		
		return server;
	}
	
	public String getTestUrl(HttpMessageSource httpMessageSource) {
		try {
			String serverName = java.net.InetAddress.getLocalHost().getHostName();
			Integer port = this.isDev() ? DEV_PORT : httpMessageSource.getPort();
			String extraParam = this.isDev() ? "/?" + HttpUtils.DEV_HTTP_PARAM + "=" + httpMessageSource.getId() : "";
			
			return "http://" + serverName + ":" + port + extraParam;
		} catch (UnknownHostException e) {
			return "unavailable";
		}
	}
	
	private boolean isDev() {
		return Environment.DEV.equals(ENVIRONMENT);
	}
}
