package com.eduhub.app.dao;

import org.springframework.http.ResponseEntity;

import com.eduhub.app.entity.User;

public interface CommonDao {
	
	public ResponseEntity<?> getCategory(String authToken);
	
	public ResponseEntity<?> getStandard(String authToken);

}
