package com.eduhub.app.service;

import org.springframework.http.ResponseEntity;

import com.eduhub.app.entity.User;

public interface AccountService {
	
	public ResponseEntity<?> createAccount(User user);
}
