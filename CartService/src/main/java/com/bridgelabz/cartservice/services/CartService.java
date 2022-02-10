package com.bridgelabz.cartservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	public static final String USER_NOT_FOUND = "OOPS! user not found in the database...!";

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private RestTemplate restTemplate;

	private static final String URL_CHECK_USER = "http://localhost:8081/user/checkuser/";
	private static final String URL_DECODE_TOKEN = "http://localhost:8081/user/decode/";

	/*** Simple hello message. ***/
	@Override
	public String helloMessage(String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			return USER_NOT_FOUND;
		} else {
			return "Hello Nikhil...!";
		}
	}

	/*** Added to cart. ***/
	@Override
	public Cart addToCart(CartDTO cart, String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new CartException(USER_NOT_FOUND);
		} else {
			Long user_id = restTemplate.getForObject(URL_DECODE_TOKEN + token, Long.class);
			Cart cartData = new Cart(cart);
			cartData.setUser_id(user_id);
			return cartRepository.save(cartData);
		}
	}

	/*** Delete cart details from the database. ***/
	@Override
	public String deleteCartDetailsId(Long cart_id, String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new CartException(USER_NOT_FOUND);
		} else {
			Optional<Cart> cartDataById = cartRepository.findById(cart_id);
			if (!cartDataById.isPresent()) {
				throw new CartException(ID_NOT_FOUND);
			} else {
				cartRepository.deleteById(cart_id);
				return "Cart details deleted from the database...!";
			}
		}
	}

	/*** Update quantity. ***/
	@Override
	public Cart updateQuantity(Long cart_id, int quantity, String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new CartException(USER_NOT_FOUND);
		} else {
			Optional<Cart> cartById = cartRepository.findById(cart_id);
			if (!cartById.isPresent()) {
				log.error(ID_NOT_FOUND);
				throw new CartException(ID_NOT_FOUND);
			} else if (quantity <= 0 || quantity > 100) {
				throw new CartException(QUANTITY_NOT_VALID);
			} else {
				Cart cart = cartById.get();
				cart.setUser_id(cartById.get().getUser_id());
				cart.setQuantity(quantity);
				return cartRepository.save(cart);
			}
		}
	}

	/*** Get all cart items. ***/
	@Override
	public List<Cart> getAllCartItems(String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new CartException(USER_NOT_FOUND);
		} else {
			return cartRepository.findAll();
		}
	}

	/*** Get all cart items for specific user. ***/
	@Override
	public List<Cart> getAllCartItemsForUser(String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new CartException(USER_NOT_FOUND);
		} else {
			Long user_id = restTemplate.getForObject(URL_DECODE_TOKEN + token, Long.class);
			return cartRepository.findCartItemsByUserId(user_id);
		}
	}

}
