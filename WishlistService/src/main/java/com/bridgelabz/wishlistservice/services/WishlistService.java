package com.bridgelabz.wishlistservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.wishlistservice.dto.WishlistDTO;
import com.bridgelabz.wishlistservice.exceptions.WishlistException;
import com.bridgelabz.wishlistservice.models.Wishlist;
import com.bridgelabz.wishlistservice.repository.WishlistRepository;

@Service
public class WishlistService implements IWishlistService {

	/*** Constant custom exception variables. ***/
	public static final String USER_NOT_FOUND = "OOPS! user not found in the database...!";
	
	@Autowired
	private WishlistRepository wishlistRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	private static final String URL_CHECK_USER = "http://localhost:8081/user/checkuser/";
	private static final String URL_DECODE_TOKEN = "http://localhost:8081/user/decode/";

	@Override
	public Wishlist addToWishlist(WishlistDTO wishlist, String token, Long book_id) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new WishlistException(USER_NOT_FOUND);
		} else {
			Long count = wishlistRepository.count();
			if (count > 0) {
				List<Wishlist> wishlisttemsByBookID = wishlistRepository.findWishlistItemsByBook_id(book_id);
				if (wishlisttemsByBookID.size() > 0) {
					WishlistDTO wishlistDTO = new WishlistDTO();
					wishlistDTO.book_id = (long) 0;
					System.out.println(wishlistDTO); // checking
					return new Wishlist(wishlistDTO);
				}
			}
			Long user_id = restTemplate.getForObject(URL_DECODE_TOKEN + token, Long.class);
			Wishlist wishlistData = new Wishlist(wishlist);
			wishlistData.setUser_id(user_id);
			wishlistData.setBook_id(book_id);
			return wishlistRepository.save(wishlistData);
		}
	}
	
	/*** Get all wishlist items for specific user. ***/
	@Override
	public List<Wishlist> getAllWishlistItemsForUser(String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new WishlistException(USER_NOT_FOUND);
		} else {
			Long user_id = restTemplate.getForObject(URL_DECODE_TOKEN + token, Long.class);
			return wishlistRepository.findWishlistItemsByUserId(user_id);
		}
	}

	/** Delete wishlist items. **/
	@Override
	public String deleteByBookId(Long bookId , String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new WishlistException(USER_NOT_FOUND);
		} else {
			Wishlist wishlistDataById = wishlistRepository.findByBookId(bookId);
			wishlistRepository.deleteById(wishlistDataById.getId());
			return "Cart details deleted from the database...!";
		}
	}
}


