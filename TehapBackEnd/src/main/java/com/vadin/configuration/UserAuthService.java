package com.vadin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vadin.entity.Users;
import com.vadin.repository.UserRepository;

@Service
public class UserAuthService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public Users loadUserByUsername(String username) throws UsernameNotFoundException {
		Users inDB = userRepository.findByUsername(username);
		if(inDB ==null)
			throw new UsernameNotFoundException("User not found");
		
		return inDB;
	}

}
