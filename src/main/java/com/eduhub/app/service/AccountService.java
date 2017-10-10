package com.eduhub.app.service;

import org.springframework.http.ResponseEntity;

import com.eduhub.app.entity.User;

public interface AccountService {
	
	public ResponseEntity<?> createAccount(User user);

	public ResponseEntity<?> changePassword(String authToken, String oldPassword, String newPassword);
	
	public ResponseEntity<?> forgetPassword(String email);
}
