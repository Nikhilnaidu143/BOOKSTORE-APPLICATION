package com.bridgelabz.orderservice.dto;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.ToString;

public @ToString class OrderDTO {
	
	@NotNull(message = "Order date cannot be null...!")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@PastOrPresent(message = "Order date cannot be future date...!")
	public LocalDate order_date;

	@NotNull(message = "Price cannot be null...!")
	@Min(value = 100 , message = "Price must be greater than 100...!")
	public double price;

	@NotNull(message = "Quantity cannot be null...!")
	@Max(value = 100 , message = "Quantity must be less than 100...!")
	public int quantity;

	@NotNull(message = "address cannot be null...!")
	@NotEmpty(message = "address cannot be empty...!")
	public String address;

	public long user_id;

	public long book_id;

}
