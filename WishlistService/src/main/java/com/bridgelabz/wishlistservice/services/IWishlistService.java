package com.bridgelabz.wishlistservice.services;

import java.util.List;

import com.bridgelabz.wishlistservice.dto.WishlistDTO;
import com.bridgelabz.wishlistservice.models.Wishlist;

public interface IWishlistService {

	public Wishlist addToWishlist(WishlistDTO wishlist, String token, Long book_id);

	public List<Wishlist> getAllWishlistItemsForUser(String token);

	public String deleteByBookId(Long bookId, String token);
	
}
