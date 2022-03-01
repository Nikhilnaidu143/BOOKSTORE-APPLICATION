package com.bridgelabz.orderservice.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bridgelabz.orderservice.dto.OrderDTO;
import com.bridgelabz.orderservice.exceptions.customexception.OrderException;
import com.bridgelabz.orderservice.models.Order;
import com.bridgelabz.orderservice.repository.OrderRepository;

@Service
public class OrderService implements IOrderService {

	/** Constant custom exception message. **/
	private static final String ID_NOT_FOUND = "OOPS! ID not found in the database...!";
	public static final String USER_NOT_FOUND = "OOPS! user not found in the database...!";

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate restTemplate;

	private static final String URL_CHECK_USER = "http://localhost:8081/user/checkuser/";
	private static final String URL_DECODE_TOKEN = "http://localhost:8081/user/decode/";
	
	/*** Simple hello message. ***/
	@Override
	public String helloMessage(String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			return USER_NOT_FOUND;
		} else {
			return "Hello Nikhil...!";
		}
	}

	/*** Placing order. ***/
	@Override
	public Order placeOrder(OrderDTO order, String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new OrderException(USER_NOT_FOUND);
		} else {
			Long user_id = restTemplate.getForObject(URL_DECODE_TOKEN + token, Long.class);
			Order orderData = new Order(order);
			LocalDate todaysDate = LocalDate.now();
			orderData.setOrder_date(todaysDate); //setting todays date as order date.
			orderData.setUser_id(user_id);
			return orderRepository.save(orderData);
		}
	}

	/*** Cancel order. ***/
	@Override
	public Order cancelOrder(Long order_id, String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new OrderException(USER_NOT_FOUND);
		} else {
			Optional<Order> orderDataById = orderRepository.findById(order_id);
			if (!orderDataById.isPresent()) {
				throw new OrderException(ID_NOT_FOUND);
			} else {
				orderDataById.get().setCancel(true);
				return orderRepository.save(orderDataById.get());
			}
		}
	}

	/*** Get all orders. ***/
	@Override
	public List<Order> getAllOrdersData(String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new OrderException(USER_NOT_FOUND);
		} else {
			return orderRepository.findOrdersWhereCancelFalse();
		}
	}

	/*** Get all order details for specific user. ***/
	@Override
	public List<Order> getAllOrdersForSpecificUser(String token) {
		boolean isUserPresent = restTemplate.getForObject(URL_CHECK_USER + token, Boolean.class);
		if (!isUserPresent) {
			throw new OrderException(USER_NOT_FOUND);
		} else {
			Long user_id = restTemplate.getForObject(URL_DECODE_TOKEN + token, Long.class);
			return orderRepository.findOrdersByUserId(user_id);
		}
	}

}
