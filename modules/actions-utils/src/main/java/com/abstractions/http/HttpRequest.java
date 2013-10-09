package com.abstractions.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;

public abstract class HttpRequest {

	protected final String url;
	private final HttpStrategy strategy;
	
	protected HttpRequest(String url, HttpStrategy strategy) {
		this.url = url;
		this.strategy = strategy;
	}
	
	public HttpResponse execute() throws ClientProtocolException, IOException {
		HttpUriRequest request = this.buildRequest();
		return this.strategy.execute(request);
	}

	protected abstract HttpUriRequest buildRequest() throws UnsupportedEncodingException;
}
