package com.abstractions.service.repository;

public class MarshallingException extends Exception {

	private static final long serialVersionUID = -8026358513427239769L;

	public MarshallingException(Exception e) {
		super(e);
	}
}
