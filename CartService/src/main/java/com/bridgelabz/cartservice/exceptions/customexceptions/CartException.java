package com.bridgelabz.cartservice.exceptions.customexceptions;

public class CartException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/*** Constructor. ***/
	public CartException(String message) {
		super(message);
	}
}
