package com.eduhub.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduhub.app.entity.User;
import com.eduhub.app.entity.UserCredential;
import com.eduhub.app.service.AuthService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserCredential logincredentials,@RequestHeader("Authentication") String password){
		logincredentials.setPassword(password);
		return authService.login(logincredentials);
	}
	
	@PostMapping(value = "/logout")
	public ResponseEntity<?> logout(@RequestHeader("AuthToken") String authToken){
		return authService.logout(authToken);
	}
}
