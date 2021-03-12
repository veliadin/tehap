package com.vadin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vadin.annotation.CurrentUser;
import com.vadin.entity.Activity;
import com.vadin.entity.Users;
import com.vadin.entity.viewmodel.ActivitySubmitViewModel;
import com.vadin.entity.viewmodel.ActivityViewModel;
import com.vadin.service.ActivityService;
import com.vadin.shared.GenericResponse;

@RestController
@RequestMapping("/api")
public class ActivityController {

	@Autowired
	ActivityService activityService;

	@PostMapping("/createActivity")
	GenericResponse saveActivity(@Valid @RequestBody ActivitySubmitViewModel activitySubmitViewModel,
			@CurrentUser Users user) {
		activityService.saveActivity(activitySubmitViewModel, user);
		return new GenericResponse("Activity is saved.");
	}

	@GetMapping("/getActivities")
	Page<ActivityViewModel> getActivites(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page) {
		return activityService.getActivities(page).map(ActivityViewModel::new);
	}

	@GetMapping({ "/getActivities/{id:[0-9]+}", "/users/{username}/activities/{id:[0-9]+}" })
	ResponseEntity<?> getActivitesRelative(@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page,
			@PathVariable long id, @PathVariable(required = false) String username,
			@RequestParam(name = "count", required = false, defaultValue = "false") boolean count,
			@RequestParam(name = "direction", defaultValue = "before") String direction) {
		if (count) {
			long newActivityCount = activityService.getNewActivitiesCount(id, username);
			Map<String, Long> response = new HashMap<>();
			response.put("count", newActivityCount);
			return ResponseEntity.ok(response);
		}
		if (direction.equals("after")) {
			List<ActivityViewModel> newActivities = activityService.getNewActivities(id, username, page.getSort())
					.stream().map(ActivityViewModel::new).collect(Collectors.toList());
			return ResponseEntity.ok(newActivities);
		}
		return ResponseEntity.ok(activityService.getOldActivities(id, username, page).map(ActivityViewModel::new));
	}

	@GetMapping("/users/{username}/activities")
	Page<ActivityViewModel> getUserActivites(@PathVariable String username,
			@PageableDefault(sort = "id", direction = Direction.DESC) Pageable page) {
		return activityService.getActivitiesOfUser(username, page).map(ActivityViewModel::new);
	}

	@DeleteMapping("/deleteActivity/{id:[0-9]+}")
	@PreAuthorize("@activitySecurityService.isAllowedToDelete(#id, principal)")
	GenericResponse deleteActivity(@PathVariable long id) {
		activityService.delete(id);
		return new GenericResponse("Activity deleted");
	}
	
	@PostMapping("/saveAttendedUser/{activityId}")
	GenericResponse saveAttendedUser(@PathVariable long activityId, @CurrentUser Users user) {
		activityService.saveAttendActivity(activityId, user);
		return new GenericResponse("ActivityAttended Completed...");	
	}
	
	@GetMapping("/findActivitiesByUserAttendedId/{id}")
	List<Activity> findActivitiesByUserAttendedId(@PathVariable long id){
		return activityService.findActivitiesByUserAttendedId(id);
	}

}
