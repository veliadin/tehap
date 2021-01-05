package com.vadin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vadin.entity.Activity;
import com.vadin.entity.Users;
import com.vadin.repository.ActivityRepository;

@Service
public class ActivitySecurityService {
	
	@Autowired
	ActivityRepository activityRepository;
	
	public boolean isAllowedToDelete(long id, Users loggedInUser) {
		Optional<Activity> inDb = activityRepository.findById(id);
		if(!inDb.isPresent()) {
			return false;
		}
		Activity activity = inDb.get();
		if(activity.getUser().getId() != loggedInUser.getId()) {
			return false;
		}
		return true;
	}

}
