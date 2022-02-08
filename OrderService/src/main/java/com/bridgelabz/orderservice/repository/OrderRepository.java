package com.bridgelabz.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.orderservice.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	//get list of orders which are not cancelled.
	@Query(value = "SELECT * FROM order_details WHERE cancel = false" ,  nativeQuery = true)
	List<Order> findOrdersWhereCancelFalse();

}
