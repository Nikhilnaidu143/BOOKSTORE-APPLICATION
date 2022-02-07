package com.bridgelabz.bookstore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.exceptions.customexceptions.BookException;
import com.bridgelabz.bookstore.models.Book;
import com.bridgelabz.bookstore.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookService implements IBookService {

	/*** Constant custom exception variables. ***/
	private static final String ID_NOT_FOUND = "OOPS! ID not found in the database...!";

	@Autowired
	private BookRepository bookRepository;

	/*** Simple hello message to check. ***/
	@Override
	public String helloMessage() {
		return "Hello Nikhil...!";
	}

	/*** adding book details in the database. ***/
	@Override
	public Book insertBook(BookDTO book) {
		return bookRepository.save(new Book(book));
	}

	/*** updating book details by id ***/
	@Override
	public Book updateBookDetails(BookDTO book, Long id) {
		Optional<Book> bookById = bookRepository.findById(id);
		if (!bookById.isPresent()) {
			log.error(ID_NOT_FOUND);
			throw new BookException(ID_NOT_FOUND);
		} else {
			return bookRepository.save(new Book(id, book));
		}
	}

	/*** Reading all books data. ***/
	@Override
	public List<Book> readAllBookDetails() {
		return bookRepository.findAll();
	}

	/*** Reading book details by ID. ***/
	@Override
	public Book readBookDetailsById(Long id) {
		Optional<Book> bookById = bookRepository.findById(id);
		if (!bookById.isPresent()) {
			throw new BookException(ID_NOT_FOUND);
		} else {
			return bookById.get();
		}
	}

	/*** Delete book details by id. ***/
	@Override
	public String deleteBookDetailsById(Long id) {
		Optional<Book> bookById = bookRepository.findById(id);
		if (bookById.isPresent()) {
			bookRepository.deleteById(id);
			return "Deleted Book details successfully";
		} else {
			return "Book is not there in database.";
		}
	}

	/*** Changing quantity. ***/
	@Override
	public Book changeQuantityById(Long id, int new_quantity) {
		Optional<Book> bookById = bookRepository.findById(id);
		if (!bookById.isPresent()) {
			throw new BookException(ID_NOT_FOUND);
		} else {
			Book book = bookById.get();
			book.setQuantity(new_quantity);
			return bookRepository.save(book);
		}
	}

	/*** Changing price. ***/
	@Override
	public Book changePriceById(Long id, String new_price) {
		Optional<Book> bookById = bookRepository.findById(id);
		if (!bookById.isPresent()) {
			throw new BookException(ID_NOT_FOUND);
		} else {
			Book book = bookById.get();
			book.setPrice(Double.parseDouble(new_price));
			return bookRepository.save(book);
		}
	}

	/*** Sort in ascending(low to high) order by price ***/
	@Override
	public List<Book> sortByPriceAscending() {
		return bookRepository.sortLowToHighByPrice();
	}

	/*** Sort in descending(high to low) order by price ***/
	@Override
	public List<Book> sortByPriceDescending() {
		return bookRepository.sortHighToLowByPrice();
	}

	/*** Search books by using book name. ***/
	@Override
	public List<Book> searchByBookName(String book_name) {
		return bookRepository.searchByBookName(book_name);
	}

}