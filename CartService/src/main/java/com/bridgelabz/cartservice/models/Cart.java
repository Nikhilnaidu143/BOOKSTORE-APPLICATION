package com.bridgelabz.cartservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.cartservice.dto.CartDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "cart_details")
public @Data class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long user_id;
	
	private Long book_id;
	
	private int quantity;

	/*** Parameterized constructor. ***/
	public Cart(CartDTO cartDTO) {
		this.user_id = cartDTO.user_id;
		this.book_id = cartDTO.book_id;
		this.quantity = cartDTO.quantity;
	}
	
	/*** Parameterized constructor(Constructor Overloading). ***/
	public Cart(Long id , CartDTO cartDTO) {
		this.id = id;
		this.user_id = cartDTO.user_id;
		this.book_id = cartDTO.book_id;
		this.quantity = cartDTO.quantity;
	}
	
}
