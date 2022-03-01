package com.bridgelabz.wishlistservice.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.wishlistservice.dto.WishlistDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "wishlist_details")
public @Data class Wishlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private long user_id;
	
	private long book_id;
	
	private int quantity;
	
	/*** Parameterized constructor. ***/
	public Wishlist(WishlistDTO wishlistDTO) {
		this.user_id = 0;
		this.book_id = wishlistDTO.book_id;
		this.quantity = wishlistDTO.quantity;
	}
	
}
