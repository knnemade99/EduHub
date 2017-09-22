package com.eduhub.app.dao;

import org.springframework.http.ResponseEntity;

import com.eduhub.app.entity.User;

public interface AccountDao {
	
	public ResponseEntity<?> createAccount(User user);
}
