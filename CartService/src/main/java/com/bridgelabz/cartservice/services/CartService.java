package com.bridgelabz.cartservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.cartservice.dto.CartDTO;
import com.bridgelabz.cartservice.exceptions.customexceptions.CartException;
import com.bridgelabz.cartservice.models.Cart;
import com.bridgelabz.cartservice.repository.CartRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartService implements ICartService {

	/*** Constant custom exception variables. ***/
	private static final String ID_NOT_FOUND = "OOPS! ID not found in the database...!";
	private static final String QUANTITY_NOT_VALID = "OOPS! Quantity must be in range 1 to 100.";

	@Autowired
	private CartRepository cartRepository;

	/*** Simple hello message. ***/
	@Override
	public String helloMessage(String token) {
		return "Hello Nikhil...!";
	}

	/*** Added to cart. ***/
	@Override
	public Cart addToCart(CartDTO cart) {
		return cartRepository.save(new Cart(cart));
	}

	/*** Delete cart details from the database. ***/
	@Override
	public String deleteCartDetailsId(Long cart_id) {
		Optional<Cart> cartDataById = cartRepository.findById(cart_id);
		if (!cartDataById.isPresent()) {
			throw new CartException(ID_NOT_FOUND);
		} else {
			cartRepository.deleteById(cart_id);
			return "Cart details deleted from the database...!";
		}
	}

	/*** Update quantity. ***/
	@Override
	public Cart updateQuantity(Long cart_id, int quantity , String token) {
		Optional<Cart> cartById = cartRepository.findById(cart_id);

		if (!cartById.isPresent()) {
			log.error(ID_NOT_FOUND);
			throw new CartException(ID_NOT_FOUND);
		} else if (quantity <= 0 || quantity > 100) {
			throw new CartException(QUANTITY_NOT_VALID);
		} else {
			Cart cart = cartById.get();
			cart.setQuantity(quantity);
			return cartRepository.save(cart);
		}
	}

	/*** Get all cart items. ***/
	@Override
	public List<Cart> getAllCartItems() {
		return cartRepository.findAll();
	}

	/*** Get cart items by id. ***/
	@Override
	public Cart getCartById(Long cart_id , String token) {
		return cartRepository.findById(cart_id).orElseThrow(() -> new CartException(ID_NOT_FOUND));
	}

}
