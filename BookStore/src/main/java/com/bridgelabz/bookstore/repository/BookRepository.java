package com.bridgelabz.bookstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.bookstore.models.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

	/*** Sorting from low to high by price. ***/
	@Query(value = "SELECT * FROM book_details ORDER BY price ASC", nativeQuery = true)
	List<Book> sortLowToHighByPrice();

	/*** Sorting from high to low by price. ***/
	@Query(value = "SELECT * FROM book_details ORDER BY price DESC", nativeQuery = true)
	List<Book> sortHighToLowByPrice();

	/***
	 * Search books with bookName (Matches with the book names which conatins 
	 * entered characters i.e, no to enter full book name).
	 ***/
	List<Book> findByBookNameContaining(String bookName);
}