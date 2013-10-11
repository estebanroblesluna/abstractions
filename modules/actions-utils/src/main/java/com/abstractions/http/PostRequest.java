package com.abstractions.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

public class PostRequest extends HttpRequest {

	private List<NameValuePair> urlParameters;

	public PostRequest(String url, HttpStrategy strategy) {
		super(url, strategy);

		this.urlParameters = new ArrayList<NameValuePair>();
	}

	@Override
	protected HttpUriRequest buildRequest() throws UnsupportedEncodingException {
		HttpPost method = new HttpPost(this.url);
		method.setEntity(new UrlEncodedFormEntity(this.urlParameters));
		return method;
	}

	public HttpRequest addFormParam(String key, String value) {
		this.urlParameters.add(new BasicNameValuePair(key, value));
		return this;
	}
}
