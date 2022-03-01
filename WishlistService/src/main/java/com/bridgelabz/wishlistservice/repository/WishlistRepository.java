package com.bridgelabz.wishlistservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.wishlistservice.models.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

	// get list of orders for specific user.
	@Query(value = "SELECT * FROM wishlist_details WHERE user_id = :user_id", nativeQuery = true)
	List<Wishlist> findWishlistItemsByUserId(Long user_id);
	
	@Query(value = "SELECT * FROM wishlist_details WHERE book_id = :book_id", nativeQuery = true)
	List<Wishlist> findWishlistItemsByBook_id(Long book_id);

	@Query(value = "SELECT * FROM wishlist_details WHERE book_id = :book_id", nativeQuery = true)
	Wishlist findByBookId(Long book_id);
	
}
