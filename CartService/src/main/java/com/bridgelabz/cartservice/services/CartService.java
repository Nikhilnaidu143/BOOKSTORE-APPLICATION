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
	public Cart addToCart(CartDTO cart, String token, Long book_id) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		Long user_id = restTemplate.getForObject(URL_DECODE_TOKEN + token, Long.class);
		boolean flag = false;
		if (!isUserPresent) {
			throw new CartException(USER_NOT_FOUND);
		} else {
			Long count = cartRepository.count();
			if (count > 0) {
				List<Cart> cartItemsByBookID = cartRepository.findCartItemsByBook_id(book_id);
				for (Cart cart2 : cartItemsByBookID) {
					if (cart2.getUser_id() == user_id) {
						flag = true;
					} else {
						flag = false;
					}
				}
				if (cartItemsByBookID.size() > 0 && flag) {
					CartDTO cartDTO = new CartDTO();
					cartDTO.book_id = (long) 0;
					System.out.println(cartDTO); // checking
					return new Cart(cartDTO);
				}
			}
			Cart cartData = new Cart(cart);
			cartData.setUser_id(user_id);
			cartData.setBook_id(book_id);
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
	public Cart updateQuantity(Long bookId, int quantity, String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		Long userId = restTemplate.getForObject(URL_DECODE_TOKEN + token, Long.class);
		if (!isUserPresent) {
			throw new CartException(USER_NOT_FOUND);
		} else {
			List<Cart> cartItemsByBookId = cartRepository.findCartItemsByBook_id(bookId);
			Cart tempCart = null;
			if (cartItemsByBookId.size() <= 0) {
				log.error(ID_NOT_FOUND);
				throw new CartException(ID_NOT_FOUND);
			} else if (quantity <= 0 || quantity > 100) {
				throw new CartException(QUANTITY_NOT_VALID);
			} else {
				for (Cart cart : cartItemsByBookId) {
					if (cart.getUser_id() == userId) {
						cart.setQuantity(quantity);
						tempCart = cart;
					} else {
						continue;
					}
				}
				return cartRepository.save(tempCart);
			}
		}
	}

	/*** Get all cart items. ***/
	@Override
	public List<Cart> getAllCartItems() {
		return cartRepository.findAll();
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

	/** Delete cart items. **/
	@Override
	public String deleteByBookId(Long bookId, String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		Long userId = restTemplate.getForObject(URL_DECODE_TOKEN + token, Long.class);
		if (!isUserPresent) {
			throw new CartException(USER_NOT_FOUND);
		} else {
			List<Cart> cartItemsByBookId = cartRepository.findCartItemsByBook_id(bookId);
			if (cartItemsByBookId.size() == 0) {
				throw new CartException(ID_NOT_FOUND);
			} else {
				for (Cart cart : cartItemsByBookId) {
					if (cart.getUser_id() == userId) {
						cartRepository.deleteById(cart.getId());
						return "Cart details deleted from the database...!";
					} else {
						continue;
					}
				}
			}
			return "Cart item not found";
		}
	}

}
