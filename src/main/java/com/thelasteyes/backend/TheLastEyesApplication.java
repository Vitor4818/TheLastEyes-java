package com.thelasteyes.backend;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class TheLastEyesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheLastEyesApplication.class, args);
	}

}
