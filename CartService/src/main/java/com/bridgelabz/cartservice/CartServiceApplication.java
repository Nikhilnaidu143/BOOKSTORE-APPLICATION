package com.bridgelabz.cartservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableEurekaClient
public class CartServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(CartServiceApplication.class, args);
		log.info("Cart Service Started in {} Environment...!", context.getEnvironment().getProperty("environment"));
		log.info("Cart Service DataBase User Is {}.",
				context.getEnvironment().getProperty("spring.datasource.username"));
	}

}
