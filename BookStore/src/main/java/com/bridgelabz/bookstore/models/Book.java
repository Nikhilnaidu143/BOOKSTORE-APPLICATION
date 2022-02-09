package com.bridgelabz.bookstore.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.bookstore.dto.BookDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "book_details")
public @Data class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "book_name")
	private String bookName;
	
	private String author;
	
	private String description;
	
	private String logo;
	
	private double price;

	private int quantity;
	
	/*** Parameterized constructor. ***/
	public Book(BookDTO bookDTO) {
		this.bookName = bookDTO.bookName;
		this.author = bookDTO.author;
		this.description = bookDTO.description;
		this.logo = bookDTO.logo;
		this.price = bookDTO.price;
		this.quantity = bookDTO.quantity;
	}
	
	/*** Parameterized constructor(Constructor Overloading). ***/
	public Book(long id , BookDTO bookDTO) {
		this.id = id;
		this.bookName = bookDTO.bookName;
		this.author = bookDTO.author;
		this.description = bookDTO.description;
		this.logo = bookDTO.logo;
		this.price = bookDTO.price;
		this.quantity = bookDTO.quantity;
	}
}