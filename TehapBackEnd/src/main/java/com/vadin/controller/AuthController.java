package com.vadin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vadin.annotation.CurrentUser;
import com.vadin.entity.Users;
import com.vadin.entity.viewmodel.UserViewModel;
import com.vadin.repository.UserRepository;

@RestController
public class AuthController {

	@Autowired
	UserRepository userRepository;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PostMapping("/api/auth")
	UserViewModel handleAuthentication(@CurrentUser Users user) {
		return new UserViewModel(user);
	}
	
	
	

}
