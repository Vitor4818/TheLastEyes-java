package com.thelasteyes.backend;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableRabbit
@SpringBootApplication
@EnableCaching
public class TheLastEyesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheLastEyesApplication.class, args);
	}

}
