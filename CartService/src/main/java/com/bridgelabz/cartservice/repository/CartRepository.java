package com.bridgelabz.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.cartservice.models.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
