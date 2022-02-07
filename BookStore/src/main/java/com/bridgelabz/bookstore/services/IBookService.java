package com.bridgelabz.bookstore.services;

import java.util.List;

import com.bridgelabz.bookstore.dto.BookDTO;
import com.bridgelabz.bookstore.models.Book;

public interface IBookService {
	
	/*** Declaring methods. ***/
	public String helloMessage();
	
	public Book insertBook(BookDTO book); 

	public Book updateBookDetails(BookDTO book, Long id);
	
	public List<Book> readAllBookDetails();

	public Book readBookDetailsById(Long id);

	public String deleteBookDetailsById(Long id);

	public Book changeQuantityById(Long id, int new_quantity);

	public Book changePriceById(Long id, String new_price);

	public List<Book> sortByPriceAscending();

	public List<Book> sortByPriceDescending();

	public List<Book> searchByBookName(String book_name);
	
}