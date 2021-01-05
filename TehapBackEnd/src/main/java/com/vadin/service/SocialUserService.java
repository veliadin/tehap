package com.vadin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vadin.entity.SocialUsers;
import com.vadin.repository.SocialUserRepository;

@Service
public class SocialUserService {

	@Autowired
	SocialUserRepository googleUserRepository;

	public void save(SocialUsers googleUsers) {
		int backGoogleUsers = googleUserRepository.existsByProviderId(googleUsers.getproviderId());
				
		if (backGoogleUsers == 0 ) {
			googleUserRepository.save(googleUsers);
		}

	}

}
