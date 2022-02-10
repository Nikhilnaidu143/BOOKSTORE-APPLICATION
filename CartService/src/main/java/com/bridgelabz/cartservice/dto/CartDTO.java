package com.bridgelabz.cartservice.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.ToString;

public @ToString class CartDTO {

	@NotNull(message = "Book ID cannot be null...!")
	public Long book_id;

	@NotNull(message = "Quantity cannot be null...!")
	@Min(value = 1, message = "Quantity must be greater than 1.")
	@Max(value = 100, message = "Quantity must be less than or equal to 100..!")
	public int quantity;

}
