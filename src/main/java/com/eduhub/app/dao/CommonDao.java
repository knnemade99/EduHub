package com.eduhub.app.dao;

import org.springframework.http.ResponseEntity;

import com.eduhub.app.entity.User;

public interface CommonDao {
	
	public ResponseEntity<?> getCategory();
	
	public ResponseEntity<?> getStandard();

}
