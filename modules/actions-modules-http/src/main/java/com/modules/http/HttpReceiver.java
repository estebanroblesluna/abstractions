package com.modules.http;

import java.io.IOException;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abstractions.api.Message;
import com.abstractions.utils.ExpressionUtils;

@WebServlet(asyncSupported = true)
public class HttpReceiver extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public static final String HTTP_RESPONSE_MESSAGE = "_HTTP_RESPONSE_MESSAGE";
	public static final String HTTP_EXCEPTION        = "_HTTP_EXCEPTION";

	private final HttpReceiverAsyncListener listener;
	private volatile HttpMessageSource messageSource;

	public HttpReceiver() {
		this.listener = new HttpReceiverAsyncListener();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void service(final HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final AsyncContext context = request.startAsync(request, response);
		final Message message = HttpUtils.readFrom(request, false);

		Long timeout = ExpressionUtils.evaluateNoFail(this.messageSource.getTimeoutExpression(), message, 0l);
		context.setTimeout(timeout);
		context.addListener(this.listener);

		context.start(new Runnable() {
			public synchronized void run() {
				try {
					Message response = messageSource.newMessage(message);
					request.setAttribute(HTTP_RESPONSE_MESSAGE, response);
				} catch (Exception e) {
					request.setAttribute(HTTP_EXCEPTION, e);
				}
				context.complete();
			}
		});
	}

	public void setMessageSource(HttpMessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
