package com.security.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.dao.UserDAO;
import com.security.entity.UserEntity;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;
	
//    private static List<String> usernameList = Arrays.asList("nyasba", "admin");
//    private static String ENCRYPTED_PASSWORD = "$2a$10$5DF/j5hHnbeHyh85/0Bdzu1HV1KyJKZRt2GhpsfzQ8387A/9duSuq"; // "password"を暗号化した値

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	UserEntity userLogin = userDAO.getUserByUsername(username);
        
        if(!username.equals(userLogin.getUsername())){
        	throw new UsernameNotFoundException(username);
        }

        return User.withUsername(username)
                .password(userLogin.getPassword())
                .authorities("ROLE_USER") // ユーザの権限
                .build();
    }

}