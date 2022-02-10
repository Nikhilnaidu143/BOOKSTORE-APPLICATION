package com.bridgelabz.orderservice.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bridgelabz.orderservice.dto.OrderDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "order_details")
public @Data class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private LocalDate order_date;

	private double price;

	private int quantity;

	private String address;

	private long user_id;

	private long book_id;

	private boolean cancel;

	/*** Parameterized constructor. ***/
	public Order(OrderDTO orderDTO) {
		this.order_date = orderDTO.order_date;
		this.price = orderDTO.price;
		this.quantity = orderDTO.quantity;
		this.address = orderDTO.address;
		this.user_id = 0;
		this.book_id = orderDTO.book_id;
		this.cancel = false;
	}

}
