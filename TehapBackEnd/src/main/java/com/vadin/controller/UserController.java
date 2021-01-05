package com.vadin.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vadin.annotation.CurrentUser;
import com.vadin.entity.SocialUsers;
import com.vadin.entity.Users;
import com.vadin.entity.viewmodel.UserUpdateViewModel;
import com.vadin.entity.viewmodel.UserViewModel;
import com.vadin.service.SocialUserService;
import com.vadin.service.UserService;
import com.vadin.shared.GenericResponse;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	SocialUserService socialUserService;

	@PostMapping("/createuser")
	public GenericResponse createUser(@Valid @RequestBody Users user) {
		userService.save(user);
		return new GenericResponse("user created");
	}

	@GetMapping("/getusers")
	public Page<UserViewModel> getUsers(Pageable page, @CurrentUser Users user) {
		return userService.getUsers(page, user).map(UserViewModel::new);// tek tek bütün user bilgilerini userviewmodel
		// constructerına yolluyor ve user objesini
		// userviewmodele çeviriyor.
	}

	@PostMapping("/loginSocial")
	public GenericResponse loginSocial(@RequestBody SocialUsers User) {
		socialUserService.save(User);
		return new GenericResponse("saved database.");
	}

	@GetMapping("/users/{username}")
	public UserViewModel getUser(@PathVariable String username) {
		Users user = userService.getByUsername(username);
		return new UserViewModel(user);
	}

	@PutMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	public UserViewModel updateUser(@Valid @RequestBody UserUpdateViewModel updatedUser, @PathVariable String username,
			@CurrentUser Users loggedUser) {
		Users user = userService.updateUser(username, updatedUser);
		return new UserViewModel(user);
	}

	@DeleteMapping("/users/delete/{username}")
	@PreAuthorize("#username == principal.username")
	GenericResponse deleteUser(@PathVariable String username) {
		userService.deleteUser(username);
		return new GenericResponse("User is deleted");
	}

}
