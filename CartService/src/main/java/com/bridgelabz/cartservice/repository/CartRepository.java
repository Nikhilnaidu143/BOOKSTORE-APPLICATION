package com.bridgelabz.cartservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.cartservice.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	// get list of orders for specific user.
	@Query(value = "SELECT * FROM cart_details WHERE user_id = :user_id", nativeQuery = true)
	List<Cart> findCartItemsByUserId(Long user_id);
}
