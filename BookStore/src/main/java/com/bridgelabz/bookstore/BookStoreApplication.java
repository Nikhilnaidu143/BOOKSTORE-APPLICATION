package com.bridgelabz.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableEurekaClient
public class BookStoreApplication {

	/** Setting rest template. **/
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BookStoreApplication.class, args);
		log.info("Book Store Started in {} Environment...!", context.getEnvironment().getProperty("environment"));
		log.info("Book Store DataBase User Is {}.", context.getEnvironment().getProperty("spring.datasource.username"));
	}

}