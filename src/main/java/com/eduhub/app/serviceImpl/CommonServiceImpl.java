package com.eduhub.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.eduhub.app.dao.CommonDao;
import com.eduhub.app.entity.User;
import com.eduhub.app.service.CommonService;

@Service("commonService")
public class CommonServiceImpl implements CommonService {
	
	@Autowired
	private CommonDao commonDao;

	@Override
	public ResponseEntity<?> getCategory(String authToken) {
		return commonDao.getCategory(authToken);
	}
	
	@Override
	public ResponseEntity<?> getStandard(String authToken) {
		return commonDao.getStandard(authToken);
	}

}
