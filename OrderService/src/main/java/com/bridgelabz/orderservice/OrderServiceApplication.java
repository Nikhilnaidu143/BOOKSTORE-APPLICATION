package com.bridgelabz.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableEurekaClient
public class OrderServiceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(OrderServiceApplication.class, args);
		log.info("Order Service Started in {} Environment...!", context.getEnvironment().getProperty("environment"));
		log.info("Order service DataBase User Is {}.",
				context.getEnvironment().getProperty("spring.datasource.username"));
	}

}
