package com.bridgelabz.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class BookStoreApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BookStoreApplication.class, args);
		log.info("Book Store Started in {} Environment...!", context.getEnvironment().getProperty("environment"));
		log.info("Book Store DataBase User Is {}.", context.getEnvironment().getProperty("spring.datasource.username"));
	}

}