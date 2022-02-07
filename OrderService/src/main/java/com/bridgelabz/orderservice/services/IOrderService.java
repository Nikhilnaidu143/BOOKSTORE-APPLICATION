package com.bridgelabz.orderservice.services;

import java.util.List;

import com.bridgelabz.orderservice.dto.OrderDTO;
import com.bridgelabz.orderservice.models.Order;

public interface IOrderService {

	/*** Declaring methods. ***/
	public String helloMessage();

	public Order placeOrder(OrderDTO order);

	public String cancelOrder(Long order_id);

	public List<Order> getAllOrdersData();

	public Order getOrdersForSpecificUser(Long id);

}
