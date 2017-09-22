package com.eduhub.app.dao;

import org.springframework.http.ResponseEntity;

import com.eduhub.app.entity.AuthTable;
import com.eduhub.app.entity.UserCredential;

public interface AuthDao {
	public ResponseEntity<?> login(UserCredential logincredentials);

	public ResponseEntity<?> logout(String authToken);
}
