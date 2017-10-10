package com.eduhub.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduhub.app.dao.AccountDao;
import com.eduhub.app.entity.User;
import com.eduhub.app.service.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao accountDao;

	@Override
	public ResponseEntity<?>createAccount(User user) {
		return accountDao.createAccount(user);
	}

	@Override
	public ResponseEntity<?> changePassword(String authToken, String oldPassword, String newPassword) {
		return accountDao.changePassword(authToken, oldPassword, newPassword);
	}

}
