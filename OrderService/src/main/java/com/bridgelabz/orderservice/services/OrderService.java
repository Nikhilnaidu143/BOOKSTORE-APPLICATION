package com.bridgelabz.orderservice.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.orderservice.dto.OrderDTO;
import com.bridgelabz.orderservice.exceptions.customexception.OrderException;
import com.bridgelabz.orderservice.models.Order;
import com.bridgelabz.orderservice.repository.OrderRepository;

@Service
public class OrderService implements IOrderService {

	/** Constant custom exception message. **/
	private static final String ID_NOT_FOUND = "OOPS! ID not found in the database...!";
	
	@Autowired
	private OrderRepository orderRepository;
	
	/*** Simple hello message. ***/
	@Override
	public String helloMessage() {
		return "Hello Nikhil...!";
	}

	/*** Placing order. ***/
	@Override
	public Order placeOrder(OrderDTO order) {
		return orderRepository.save(new Order(order));
	}

	/*** Cancel order. ***/
	@Override
	public Order cancelOrder(Long order_id) {
		Optional<Order> orderDataById = orderRepository.findById(order_id);
		if(!orderDataById.isPresent()) {
			throw new OrderException(ID_NOT_FOUND);
		}
		else {
			orderDataById.get().setCancel(true);
			return orderRepository.save(orderDataById.get());
		}
	}

	/*** Get all orders. ***/
	@Override
	public List<Order> getAllOrdersData() {
		return orderRepository.findOrdersWhereCancelFalse();
	}

	/*** Get order details for specific user. ***/
	@Override
	public Order getOrdersForSpecificUser(Long id) {
		return orderRepository.findById(id).orElseThrow(() -> new OrderException(ID_NOT_FOUND));
	}

}
