package com.bridgelabz.wishlistservice.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.wishlistservice.dto.ResponseDTO;
import com.bridgelabz.wishlistservice.dto.WishlistDTO;
import com.bridgelabz.wishlistservice.models.Wishlist;
import com.bridgelabz.wishlistservice.services.IWishlistService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/wishlist")
@Slf4j
@CrossOrigin("http://localhost:4200")
public class WishlistController {

	@Autowired
	private IWishlistService wishlistService;
	
	/*** Add to cart ***/
	@PostMapping(value = "/add/{token}/{book_id}")
	public ResponseEntity<ResponseDTO> insert(@PathVariable String token, @PathVariable Long book_id,
			@Valid @RequestBody WishlistDTO wishlist) {
		log.info("Wishlist DTO :- " + wishlist.toString()); // logging.
		Wishlist wishlistData = wishlistService.addToWishlist(wishlist, token, book_id);
		ResponseDTO responseDTO = new ResponseDTO("Added to wishlist successfully..!", wishlistData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Get all wishlist items for specific user. ***/
	@GetMapping(value = "/get/{token}")
	public ResponseEntity<ResponseDTO> get(@PathVariable String token) {
		List<Wishlist> wishlistData = wishlistService.getAllWishlistItemsForUser(token);
		ResponseDTO responseDTO = new ResponseDTO("Get wishlist items for user successfull..!", wishlistData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "delete/{bookId}/{token}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable Long bookId , @PathVariable String token) {
		String deleteMssg = wishlistService.deleteByBookId(bookId , token);
		ResponseDTO responseDTO = new ResponseDTO("delete wishlist item successfull..!", bookId);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
}
