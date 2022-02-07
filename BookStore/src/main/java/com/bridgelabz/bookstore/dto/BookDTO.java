package com.bridgelabz.bookstore.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class BookDTO {

	@NotNull(message = "Book name cannot be null...!")
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "Book name validation failed..!")
	public String book_name;

	@NotNull(message = "Author name cannot be null...!")
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message = "Author name validation failed..!")
	public String author;

	@NotNull(message = "Description cannot be null...!")
	public String description;

	@NotNull(message = "Logo cannot be null...!")
	public String logo;

	@NotNull(message = "Price cannot be null...!")
	@Min(value = 100 , message = "Price must be greater than 100...!")
	public double price;

	@NotNull(message = "Quantity cannot be null...!")
	@Max(value = 100 , message = "Quantity must be less than 100...!")
	public int quantity;

}