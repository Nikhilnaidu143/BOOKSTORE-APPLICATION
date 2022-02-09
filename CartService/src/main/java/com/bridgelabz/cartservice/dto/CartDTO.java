package com.bridgelabz.cartservice.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import lombok.ToString;

public @ToString class CartDTO {

	@NotNull(message = "User ID cannot be null...!")
	public Long user_id;

	@NotNull(message = "Book ID cannot be null...!")
	public Long book_id;

	@NotNull(message = "Quantity cannot be null...!")
	@Max(value = 100, message = "Quantity must be less than or equal to 100..!")
	public int quantity;

}
