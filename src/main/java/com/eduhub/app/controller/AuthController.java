package com.eduhub.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eduhub.app.entity.AuthTable;
import com.eduhub.app.entity.UserCredential;
import com.eduhub.app.service.AuthService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthService commonService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody UserCredential logincredentials){
		return commonService.login(logincredentials);
	}
}
