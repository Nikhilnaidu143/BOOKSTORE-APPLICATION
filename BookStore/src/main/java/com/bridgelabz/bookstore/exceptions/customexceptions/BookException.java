package com.bridgelabz.bookstore.exceptions.customexceptions;

public class BookException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** Constructor. **/
	public BookException(String message) {
		super(message);
	}
}