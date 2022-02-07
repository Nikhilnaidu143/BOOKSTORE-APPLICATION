package com.bridgelabz.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.orderservice.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
