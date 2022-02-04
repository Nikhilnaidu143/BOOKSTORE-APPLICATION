package com.bridgelabz.userregistration.exceptions.customexception;

public class UserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/** Constructor. **/
	public UserException(String message) {
		super(message);
	}
}