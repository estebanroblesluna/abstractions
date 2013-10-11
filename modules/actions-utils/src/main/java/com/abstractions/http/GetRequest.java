package com.abstractions.http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class GetRequest extends HttpRequest {

	public GetRequest(String url, HttpStrategy strategy) {
		super(url, strategy);
	}

	@Override
	protected HttpUriRequest buildRequest() {
		return new HttpGet(this.url);
	}
}
