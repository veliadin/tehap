package com.vadin.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.vadin.annotation.UniqueUsername;
import com.vadin.entity.Users;
import com.vadin.repository.UserRepository;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername,String> {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		Users user = userRepository.findByUsername(username);
		if(user != null) {
			return false;
		}
		return true;
	}

}
