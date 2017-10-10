package com.eduhub.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootApplication
@Configuration
public class EduHubApplication {

	public static void main(String[] args) {
		final Logger logger = LoggerFactory.getLogger(EduHubApplication.class);	
		SpringApplication.run(EduHubApplication.class, args);
		System.out.println("Application Started");
	}
}
