package com.bridgelabz.orderservice.services;

import java.util.List;

import com.bridgelabz.orderservice.dto.OrderDTO;
import com.bridgelabz.orderservice.models.Order;

public interface IOrderService {

	/*** Declaring methods. ***/
	public String helloMessage(String token);

	public Order placeOrder(OrderDTO order, String token);

	public Order cancelOrder(Long order_id, String token);

	public List<Order> getAllOrdersData();

	public Order getOrdersForSpecificUser(Long id, String token);

}
