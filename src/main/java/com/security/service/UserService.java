package com.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.security.dao.UserDAO;
import com.security.entity.UserEntity;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	public List<UserEntity> getAllUser() {
		// TODO Auto-generated method stub
		return userDAO.getAllUser();
		//return null;
	}

}
