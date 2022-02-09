package com.bridgelabz.cartservice.services;

import java.util.List;

import com.bridgelabz.cartservice.dto.CartDTO;
import com.bridgelabz.cartservice.models.Cart;

public interface ICartService {

	/*** Declaring methods. ***/
	public String helloMessage(String token);

	public Cart addToCart(CartDTO cart);

	public String deleteCartDetailsId(Long cart_id);

	public Cart updateQuantity(Long cart_id, int quantity, String token);

	public List<Cart> getAllCartItems();

	public Cart getCartById(Long cart_id, String token);

}
