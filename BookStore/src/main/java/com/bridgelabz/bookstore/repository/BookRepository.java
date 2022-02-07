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
	
	@Query(value = "SELECT * FROM book_details WHERE book_name = :book_name", nativeQuery = true)
	List<Book> searchByBookName(String book_name);

}