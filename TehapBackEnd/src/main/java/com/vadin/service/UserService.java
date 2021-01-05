package com.vadin.service;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vadin.entity.Users;
import com.vadin.entity.viewmodel.UserUpdateViewModel;
import com.vadin.error.NotFoundException;
import com.vadin.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	FileService fileService;
	
	public void save(Users user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	public Page<Users> getUsers(Pageable page, Users user){
		if(user!=null) {
			return userRepository.findByUsernameNot(user.getUsername(), page);
		}
		return userRepository.findAll(page);
	}
	public Users getByUsername(String username) {
		Users inDB = userRepository.findByUsername(username);
		if(inDB == null) {
			throw new NotFoundException();
		}
		return inDB;
	}
	public Users updateUser(String username, UserUpdateViewModel updatedUser) {
		Users user = getByUsername(username);
		user.setDisplayName(updatedUser.getDisplayName());
		if(updatedUser.getImage() != null) {
			String oldImageName = user.getImage();
			//user.setImage(updatedUser.getImage());
			try {
				String storedFileName = fileService.writeBase64EncodedStringToFile(updatedUser.getImage());
				user.setImage(storedFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileService.deleteProfileImage(oldImageName);
			
		}
		return userRepository.save(user);
	}
	public void deleteUser(String username) {
		Users inDb = userRepository.findByUsername(username);
		fileService.deleteAllStoredFilesForUser(inDb);
		userRepository.delete(inDb);
	}



}
