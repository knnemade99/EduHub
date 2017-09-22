package com.eduhub.app.service;

import org.springframework.http.ResponseEntity;

import com.eduhub.app.entity.UserCredential;

public interface AuthService {
	public ResponseEntity<?> login(UserCredential logincredentials);

	public ResponseEntity<?> logout(String authToken);
}
