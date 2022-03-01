package com.bridgelabz.bookstore.services;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.models.Book;

public interface IBookService {

	/*** Declaring methods. ***/
	public String helloMessage(String token);

	public Book insertBook(BookDTO book);

	public Book updateBookDetails(BookDTO book, Long id, String token);

	public List<Book> readAllBookDetails(String token);

	public Book readBookDetailsById(Long id, String token);

	public String deleteBookDetailsById(Long id, String token);

	public Book changeQuantityById(Long id, int new_quantity, String token);

	public Book changePriceById(Long id, String new_price, String token);

	public List<Book> sortByPriceAscending(String token);

	public List<Book> sortByPriceDescending(String token);

	public List<Book> searchByBookName(String book_name, String token);

}