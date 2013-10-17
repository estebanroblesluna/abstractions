package com.abstractions.http;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

public class HttpStrategy {

	private static final Log log = LogFactory.getLog(HttpStrategy.class);
	
	private volatile DefaultHttpClient client;
	private volatile ThreadSafeClientConnManager connManager;
	private volatile int connectionTimeout;
	private volatile int soTimeout;

	public HttpStrategy() {
		this.connectionTimeout = 3000;
		this.soTimeout = 3000;
		this.initialize();
	}

	public void initialize() {
		if (this.client != null) {
			this.client = null;
			this.connManager.shutdown();
		}
		
		this.connManager = new ThreadSafeClientConnManager();
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, this.connectionTimeout);
		HttpConnectionParams.setSoTimeout(params, this.soTimeout);

		this.connManager.setMaxTotal(100);
		this.connManager.setDefaultMaxPerRoute(10);

		this.client = new DefaultHttpClient(this.connManager, params);
		this.client.getParams().setParameter(
				ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
	}

	public void close(HttpResponse response) {
		if (response != null) {
			this.close(response.getEntity());
		}
	}
	
	public void close(HttpEntity entity) {
		if (entity != null) {
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
				log.warn("Error consuming entity");
			}
		}
	}
	public HttpResponse execute(HttpUriRequest request) throws ClientProtocolException, IOException {
		return this.client.execute(request);
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}

	public GetRequest get(String url) {
		return new GetRequest(url, this);
	}

	public PostRequest post(String url) {
		return new PostRequest(url, this);
	}
}
