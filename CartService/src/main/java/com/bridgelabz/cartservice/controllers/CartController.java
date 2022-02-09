package com.bridgelabz.cartservice.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.cartservice.dto.CartDTO;
import com.bridgelabz.cartservice.dto.ResponseDTO;
import com.bridgelabz.cartservice.models.Cart;
import com.bridgelabz.cartservice.services.ICartService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cart")
@Slf4j
public class CartController {
	
	@Autowired // AutoWired annotation is used for automatic dependency injection.
	private ICartService cartService;

	/*** Simple hello message for checking. ***/
	@GetMapping(value = { "", "/", "/home" })
	public ResponseEntity<ResponseDTO> sayHello(@RequestHeader(name = "token") String token) {
		String mssg = cartService.helloMessage(token);
		ResponseDTO responseDTO = new ResponseDTO("Get Call successfull.", mssg);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	

	/*** Add to cart ***/
	@PostMapping(value = "/add")
	public ResponseEntity<ResponseDTO> insert(@Valid @RequestBody CartDTO cart) {
		log.info("Cart DTO :- " + cart.toString()); // logging.
		Cart cartData = cartService.addToCart(cart);
		ResponseDTO responseDTO = new ResponseDTO("Added to cart successfully..!", cartData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Delete Book details by using ID. ***/
	@DeleteMapping(value = "/remove/{cart_id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable Long cart_id) {
		String deletedMessage = cartService.deleteCartDetailsId(cart_id);
		ResponseDTO responseDTO = new ResponseDTO("Delete Call for Cart successfull..!", deletedMessage);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Update quantity. ***/
	@PutMapping(value = "update/{cart_id}")
	public ResponseEntity<ResponseDTO> update(@PathVariable Long cart_id , @RequestParam(value = "quantity") int quantity , @RequestHeader(name = "token") String token) {
		Cart cartData = cartService.updateQuantity(cart_id , quantity , token);
		ResponseDTO responseDTO = new ResponseDTO("Update Call for quantity successfull..!", cartData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Get All cart items. ***/
	@GetMapping(value = "getAll")
	public ResponseEntity<ResponseDTO> getALL() {
		List<Cart> AllcartItems = cartService.getAllCartItems();
		ResponseDTO responseDTO = new ResponseDTO("Get All Call for Cart items successfull..!", AllcartItems);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Get Cart by id. ***/
	@GetMapping(value = "get/{cart_id}")
	public ResponseEntity<ResponseDTO> get(@PathVariable Long cart_id , @RequestHeader(name = "token") String token) {
		Cart cartData = cartService.getCartById(cart_id , token);
		ResponseDTO responseDTO = new ResponseDTO("Get Call for Cart successfull..!", cartData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
}
