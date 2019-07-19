package com.macyscart.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.macyscart.demo.service.CartInfoServiceImpl;

@EnableJpaRepositories
@ComponentScan
@SpringBootApplication
public class MacysCartApplication {
	String name = "Rajesh";

	public static void main(String[] args) {
		SpringApplication.run(MacysCartApplication.class, args);
	}

}
