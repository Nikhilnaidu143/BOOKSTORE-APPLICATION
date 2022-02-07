package com.bridgelabz.bookstore.controllers;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.dto.ResponseDTO;
import com.bridgelabz.bookstore.models.Book;
import com.bridgelabz.bookstore.services.IBookService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

	@Autowired // AutoWired annotation is used for automatic dependency injection.
	private IBookService bookService;

	/*** Simple hello message for checking. ***/
	@GetMapping(value = { "", "/", "/home" })
	public ResponseEntity<ResponseDTO> sayHello() {
		String mssg = bookService.helloMessage();
		ResponseDTO responseDTO = new ResponseDTO("Get Call successfull.", mssg);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Adding book details in the database. ***/
	@PostMapping(value = "/add")
	public ResponseEntity<ResponseDTO> insert(@Valid @RequestBody BookDTO book) {
		log.info("Book DTO :- " + book.toString()); // logging.
		Book bookData = bookService.insertBook(book);
		ResponseDTO responseDTO = new ResponseDTO("Post Call for Book successfull..!", bookData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Update book details by id. ***/
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<ResponseDTO> update(@Valid @RequestBody BookDTO book, @PathVariable Long id) {
		Book bookData = bookService.updateBookDetails(book, id);
		ResponseDTO responseDTO = new ResponseDTO("Put Call for Book successfull..!", bookData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Get All books data. ***/
	@GetMapping(value = "/readAll")
	public ResponseEntity<ResponseDTO> readAll() {
		List<Book> booksData = bookService.readAllBookDetails();
		ResponseDTO responseDTO = new ResponseDTO("Get All Call for Books successfull..!", booksData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}

	/*** Get book details by id. ***/
	@GetMapping(value = "/read/{id}")
	public ResponseEntity<ResponseDTO> read(@PathVariable Long id) {
		Book bookData = bookService.readBookDetailsById(id);
		ResponseDTO responseDTO = new ResponseDTO("Get Call for ID successfull..!", bookData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Delete Book details by using ID. ***/
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
		String deletedMessage = bookService.deleteBookDetailsById(id);
		ResponseDTO responseDTO = new ResponseDTO("Delete Call for Book successfull..!", deletedMessage);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Change quantity by using ID. ***/
	@PutMapping(value = "quantity/{id}/{new_quantity}")
	public ResponseEntity<ResponseDTO> changeQuantity(@PathVariable Long id , @PathVariable int new_quantity) {
		Book bookData = bookService.changeQuantityById(id , new_quantity);
		ResponseDTO responseDTO = new ResponseDTO("Quantity changed successfully..!", bookData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Change price by using ID. ***/
	@PutMapping(value = "price/{id}/{new_price}")
	public ResponseEntity<ResponseDTO> changePrice(@PathVariable Long id , @PathVariable String new_price) {
		Book bookData = bookService.changePriceById(id , new_price);
		ResponseDTO responseDTO = new ResponseDTO("Price changed successfully..!", bookData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Get All books data after sorting low to high by price. ***/
	@GetMapping(value = "/sortASC")
	public ResponseEntity<ResponseDTO> sortInAscending() {
		List<Book> booksData = bookService.sortByPriceAscending();
		ResponseDTO responseDTO = new ResponseDTO("Sort Books in Ascending order successfull..!", booksData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Get All books data after sorting high to low by price. ***/
	@GetMapping(value = "/sortDESC")
	public ResponseEntity<ResponseDTO> sortInDescending() {
		List<Book> booksData = bookService.sortByPriceDescending();
		ResponseDTO responseDTO = new ResponseDTO("Sort Books in Descending order successfull..!", booksData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}
	
	/*** Search book data by book name. ***/
	@GetMapping(value = "/search/{book_name}")
	public ResponseEntity<ResponseDTO> sortInDescending(@PathVariable String book_name) {
		List<Book> booksData = bookService.searchByBookName(book_name);
		ResponseDTO responseDTO = new ResponseDTO("Search Books by book_name successfull..!", booksData);
		return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK);
	}	
}