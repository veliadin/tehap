package com.vadin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vadin.annotation.CurrentUser;
import com.vadin.entity.Users;
import com.vadin.service.FollowersService;

@RestController
@RequestMapping("/api")
public class FollowersController {

	@Autowired
	FollowersService followersService;

	@GetMapping(value = "/getFollowersCount/{username}")
	public long getFollowersCount(@PathVariable String username) {
		return followersService.getFollowers(username);
	}

	@PostMapping(value = "/follow/{username}")
	public void saveFollow(@CurrentUser Users loggedUser, @PathVariable String username) {
		followersService.saveFollow(loggedUser, username);
	}

	@DeleteMapping(value = "/unfollow/{username}")
	public int unfollow(@CurrentUser Users loggedUser, @PathVariable String username) {
		return followersService.unfollow(loggedUser, username);
	}

	@GetMapping(value = "/getFollowingCount/{username}")
	public long getFollowingCount(@PathVariable String username) {
		return followersService.getFollowing(username);
	}

	@GetMapping(value = "/getTheUserFollow/{username}")
	public boolean theUserFollow(@CurrentUser Users loggedUser, @PathVariable String username) {
		return followersService.theUserFollow(loggedUser, username);
	}

}
