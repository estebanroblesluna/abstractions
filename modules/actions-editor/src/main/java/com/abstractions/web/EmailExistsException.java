package com.abstractions.web;

/**
 * 
 * @author Guido J. Celada (celadaguido@gmail.com)
 *
 */
public class EmailExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailExistsException() {
		super();
	}

	public EmailExistsException(String message) {
		super(message);
	}

	public EmailExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailExistsException(Throwable cause) {
		super(cause);
	}
}
