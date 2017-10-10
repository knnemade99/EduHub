package com.eduhub.app.service;

import org.springframework.http.ResponseEntity;

import com.eduhub.app.entity.User;

public interface CommonService {
	
	public ResponseEntity<?> getCategory(String authToken);
	
	public ResponseEntity<?> getStandard(String authToken);
	
}
