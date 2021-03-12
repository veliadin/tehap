package com.vadin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vadin.entity.Followers;
import com.vadin.entity.Users;
import com.vadin.repository.FollowerRepository;
import com.vadin.repository.UserRepository;

@Service
public class FollowersService {

	@Autowired
	FollowerRepository followersRepository;

	@Autowired
	UserRepository userRepository;

	public Long getFollowers(String username) {
		Users user = userRepository.findByUsername(username);
		return followersRepository.userFollowersCount(user.getId());
	}

	public void saveFollow(Users loggedUser, String followingUser) {

		Followers followReq = new Followers();
		Users followingUserE = userRepository.findByUsername(followingUser);
		followReq.setFrom(loggedUser);
		followReq.setTo(followingUserE);
		int val = followersRepository.theUserFollow(loggedUser, followingUserE);
		if (val > 0) {
			System.out.println("Already following...");
			return;
		} else {
			followersRepository.save(followReq);
		}
	}

	public int unfollow(Users loggedUser, String username) {

		//Followers unFollowReq = new Followers();
		Users followingUserE = userRepository.findByUsername(username);
		//unFollowReq.setFrom(loggedUser);
		//unFollowReq.setTo(followingUserE);
		return followersRepository.deleteBytoIdandfromId(loggedUser,followingUserE);
	}

	public long getFollowing(String username) {
		Users user = userRepository.findByUsername(username);
		return followersRepository.userFollowingCount(user.getId());
	}
	
	public boolean theUserFollow(Users loggedUser, String followingUser) {
		Followers followerReq = new Followers();
		Users followingUserE = userRepository.findByUsername(followingUser);
		followerReq.setFrom(loggedUser);
		followerReq.setTo(followingUserE);
		
		int vat = followersRepository.theUserFollow(loggedUser, followingUserE);
		if(vat>0) {
			return true;
		}else {
			return false;
		}
	}

}
