package com.eduhub.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
public class EduHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduHubApplication.class, args);
	}
}
