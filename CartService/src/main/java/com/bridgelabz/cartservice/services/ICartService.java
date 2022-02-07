package com.bridgelabz.cartservice.services;

import java.util.List;

import com.bridgelabz.cartservice.dto.CartDTO;
import com.bridgelabz.cartservice.models.Cart;

public interface ICartService {

	/*** Declaring methods. ***/
	public String helloMessage();

	public Cart addToCart(CartDTO cart);

	public String deleteCartDetailsId(Long cart_id);

	public Cart updateQuantity(Long cart_id, int quantity);

	public List<Cart> getAllCartItems();

	public Cart getCartById(Long cart_id);
	
}
