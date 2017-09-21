package com.eduhub.app.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Controller {

	public static void main(String[] args) {
		SpringApplication.run(Controller.class, args);
		System.out.println("EduData Started");
	}
}
