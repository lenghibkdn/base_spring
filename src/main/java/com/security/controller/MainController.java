package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.security.entity.User;
import com.security.service.UserService;

@RestController
public class MainController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUser(){
		List<User> listUser = userService.getAllUser();
		return new ResponseEntity<List<User>>(listUser, HttpStatus.OK);
	}
	
	
	
}
