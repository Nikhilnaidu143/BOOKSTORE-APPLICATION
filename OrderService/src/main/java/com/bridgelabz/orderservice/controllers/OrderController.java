package com.bridgelabz.orderservice.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.orderservice.dto.OrderDTO;
import com.bridgelabz.orderservice.dto.ResponseDTO;
import com.bridgelabz.orderservice.models.Order;
import com.bridgelabz.orderservice.services.IOrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

	@Autowired // AutoWired annotation is used for automatic dependency injection.
	private IOrderService orderService;

	/*** Simple hello message for checking. ***/
	@GetMapping(value = { "", "/", "/home" })
	public ResponseEntity<ResponseDTO> sayHello(@RequestHeader(name = "token") String token) {
		String mssg = orderService.helloMessage(token);
		ResponseDTO responseDTO = new ResponseDTO("Get Call successfull.", mssg);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Place order. ***/
	@PostMapping(value = "/place")
	public ResponseEntity<ResponseDTO> insert(@Valid @RequestBody OrderDTO order,
			@RequestHeader(name = "token") String token) {
		log.info("Order DTO :- " + order.toString()); // logging.
		Order orderData = orderService.placeOrder(order , token);
		ResponseDTO responseDTO = new ResponseDTO("Order placed successfully..!", orderData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Cancel order. ***/
	@PutMapping(value = "/cancel/{order_id}")
	public ResponseEntity<ResponseDTO> cancel(@PathVariable Long order_id,
			@RequestHeader(name = "token") String token) {
		Order canceledOrderData = orderService.cancelOrder(order_id, token);
		ResponseDTO responseDTO = new ResponseDTO("Order cancelled successfully..!", canceledOrderData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Get all orders. ***/
	@GetMapping(value = "getAll")
	public ResponseEntity<ResponseDTO> getAll() {
		List<Order> ordersData = orderService.getAllOrdersData();
		ResponseDTO responseDTO = new ResponseDTO("Get Call for all orders successfull..!", ordersData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Get order details for specific user. ***/
	@GetMapping(value = "get/{id}")
	public ResponseEntity<ResponseDTO> get(@PathVariable Long id, @RequestHeader(name = "token") String token) {
		Order orderData = orderService.getOrdersForSpecificUser(id, token);
		ResponseDTO responseDTO = new ResponseDTO("Get Call for specific user successfull..!", orderData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

}
