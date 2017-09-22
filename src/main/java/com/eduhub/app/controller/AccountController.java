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
import com.eduhub.app.service.AccountService;
import com.eduhub.app.service.AuthService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/account")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping(value = "/create")
	public ResponseEntity<?> createAccount(@RequestBody User user){
		return accountService.createAccount(user);
	}
}
