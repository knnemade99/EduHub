package com.eduhub.app.serviceImpl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.MimeMessageHelper;


import com.eduhub.app.dao.AuthDao;
import com.eduhub.app.entity.AuthTable;
import com.eduhub.app.entity.UserCredential;
import com.eduhub.app.service.AuthService;
import com.eduhub.app.util.EmailSender;
import com.eduhub.app.util.Encrypt;

@Service("authService")
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthDao authDao;

	@Override
	public ResponseEntity<?> login(UserCredential logincredentials){
		logincredentials.setPassword(Encrypt.encrypt(logincredentials.getPassword())); 
		return authDao.login(logincredentials);
	}

	@Override
	public ResponseEntity<?> logout(String authToken) {
		return authDao.logout(authToken);
	}

}
