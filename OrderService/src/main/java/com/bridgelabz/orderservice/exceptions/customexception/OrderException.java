package com.bridgelabz.orderservice.exceptions.customexception;

public class OrderException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/** Constructor. **/
	public OrderException(String message) {
		super(message);
	}

}
