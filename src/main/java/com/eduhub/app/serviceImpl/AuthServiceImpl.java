package com.eduhub.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduhub.app.dao.AuthDao;
import com.eduhub.app.entity.AuthTable;
import com.eduhub.app.entity.UserCredential;
import com.eduhub.app.service.AuthService;
import com.eduhub.app.util.Encrypt;

@Service("commonService")
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthDao authDao;

	@Override
	public ResponseEntity<?> login(UserCredential logincredentials) {
		logincredentials.setPassword(Encrypt.encrypt(logincredentials.getPassword()));
		return authDao.login(logincredentials);
	}

}
