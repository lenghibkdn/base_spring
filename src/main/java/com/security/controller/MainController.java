package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.security.entity.UserEntity;
import com.security.service.AmazonClient;
import com.security.service.UserService;

@RestController
public class MainController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AmazonClient amazonClient;
	
	
	@RequestMapping("/public")
	public String apiPublic() {
		return "this is public api";
	}
	
	@RequestMapping(value = "/private", method = RequestMethod.GET)
	public ResponseEntity<List<UserEntity>> listAllUser(){
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = (String) (authentication.getPrincipal());
		
		System.out.println(username);
		
		List<UserEntity> listUser = userService.getAllUser();
		return new ResponseEntity<List<UserEntity>>(listUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	public String uploadImage(@RequestPart(value = "file") MultipartFile file) {
		return this.amazonClient.uploadFile(file);
	}
		
	
}
