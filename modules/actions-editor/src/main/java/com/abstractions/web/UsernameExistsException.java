package com.abstractions.web;

public class UsernameExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsernameExistsException() {
		super();
	}

	public UsernameExistsException(String message) {
		super(message);
	}

	public UsernameExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameExistsException(Throwable cause) {
		super(cause);
	}
}
