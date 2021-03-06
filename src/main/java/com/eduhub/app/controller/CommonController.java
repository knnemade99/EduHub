package com.eduhub.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduhub.app.entity.User;
import com.eduhub.app.service.CommonService;

@CrossOrigin
@RestController
@EnableAutoConfiguration
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	private CommonService commonService;
	
	@GetMapping(value = "/getcategory")
	public ResponseEntity<?> getCategory(){
		return commonService.getCategory();
	}
	
	@GetMapping(value = "/getstandard")
	public ResponseEntity<?> getStandard(){
		return commonService.getStandard();
	}
}
