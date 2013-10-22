package com.modules.http;

import java.io.IOException;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.abstractions.api.Message;
import com.abstractions.utils.MessageUtils;

public class HttpReceiverAsyncListener implements AsyncListener {
	
	@Override
	public void onComplete(AsyncEvent event) throws IOException {
		Exception e = (Exception) event.getAsyncContext().getRequest().getAttribute(HttpReceiver.HTTP_EXCEPTION);
		ServletResponse response = event.getAsyncContext().getResponse();

		if (e != null) {
			if (response instanceof HttpServletResponse) {
				((HttpServletResponse) response).setStatus(500);
				IOUtils.write(ExceptionUtils.getFullStackTrace(e), response.getWriter());
			}
		} else {

			Message message = (Message) event.getAsyncContext().getRequest().getAttribute(HttpReceiver.HTTP_RESPONSE_MESSAGE);
			if (message != null) {
				Object contentType = message.getProperty(MessageUtils.HTTP_BASE_PROPERTY + ".contentType");
				if (contentType != null) {
					response.setContentType(contentType.toString());
				}
				Object result = message.getPayload();
				
				if (result == null) {
					IOUtils.write("", response.getWriter());
				} else if (result instanceof String) {
					IOUtils.write((String)result, response.getWriter());
				} else if (result instanceof byte[]) {
					response.getOutputStream().write((byte[])result);
				}
			}
		}
	}

	@Override
	public void onTimeout(AsyncEvent event) throws IOException {
		ServletResponse response = event.getAsyncContext().getResponse();

		if (response instanceof HttpServletResponse) {
			((HttpServletResponse) response).setStatus(408);
		}

		IOUtils.write("Timeout reached", event.getAsyncContext().getResponse().getWriter());
	}

	@Override
	public void onError(AsyncEvent event) throws IOException {
		ServletResponse response = event.getAsyncContext().getResponse();
		if (response instanceof HttpServletResponse) {
			((HttpServletResponse) response).setStatus(500);
			Throwable e = event.getThrowable();
			if (e != null) {
				IOUtils.write(ExceptionUtils.getFullStackTrace(e), response.getWriter());
			} else {
				IOUtils.write("Unknown error", event.getAsyncContext().getResponse().getWriter());
			}
		}
	}

	@Override
	public void onStartAsync(AsyncEvent event) throws IOException {
		// DO NOTHING
	}
}
