package com.vadin.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.vadin.entity.Activity;
import com.vadin.entity.FileAttachment;
import com.vadin.entity.Users;
import com.vadin.entity.viewmodel.ActivitySubmitViewModel;
import com.vadin.repository.ActivityRepository;
import com.vadin.repository.FileAttachmentRepository;

@Service
public class ActivityService {

	ActivityRepository activityRepository;

	UserService userService;

	FileAttachmentRepository fileAttachmentRepository;

	FileService fileService;

	public ActivityService(ActivityRepository activityRepository, 
			UserService userService,
			FileAttachmentRepository fileAttachmentRepository,
			FileService fileService) {
		super();
		this.activityRepository = activityRepository;
		this.fileAttachmentRepository = fileAttachmentRepository;
		this.fileService = fileService;
		this.userService = userService;
	}
	
	public void saveAttendActivity(long activityId, Users user) {
		try {
			activityRepository.saveAttendUser(activityId, user.getId());
			System.out.println("saveAttendActivity Correct");
		} catch (Exception e) {
			System.out.println("saveAttendActivity Error : " +e.toString());
		}
		
	}
	
	
	public void saveActivity(ActivitySubmitViewModel activitySubmitViewModel, Users user) {
		Activity activity = new Activity();
		activity.setTitle(activitySubmitViewModel.getTitle());
		activity.setTimestamp(new Date());
		activity.setUser(user);
		
		activity.setDescription(activitySubmitViewModel.getDescription());
		activity.setLocation(activitySubmitViewModel.getLocation());
		activity.setActivityHour(activitySubmitViewModel.getActivityHour());
		activity.setActivityMinute(activitySubmitViewModel.getActivityMinute());
		activity.setStartDate(activitySubmitViewModel.getStartDate());
		
		activityRepository.save(activity);
		Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository
				.findById(activitySubmitViewModel.getAttachmentId());
		if (optionalFileAttachment.isPresent()) {
			FileAttachment fileAttachment = optionalFileAttachment.get();
			fileAttachment.setActivity(activity);
			fileAttachmentRepository.save(fileAttachment);
		}
	}

	public Page<Activity> getActivities(Pageable page) {
		return activityRepository.findAll(page);
	}

	public Page<Activity> getActivitiesOfUser(String username, Pageable page) {
		Users inDb = userService.getByUsername(username);
		return activityRepository.findByUser(inDb, page);

	}

	public Page<Activity> getOldActivities(long id, String username, Pageable page) {
		Specification<Activity> specification = idLessThan(id);
		if (username != null) {
			Users inDb = userService.getByUsername(username);
			specification = specification.and(userIs(inDb));
		}
		return activityRepository.findAll(specification, page);
	}

	public long getNewActivitiesCount(long id, String username) {
		Specification<Activity> specification = idGreaterThan(id);
		if (username != null) {
			Users inDb = userService.getByUsername(username);
			specification = specification.and(userIs(inDb));
		}
		return activityRepository.count(specification);
	}

	public List<Activity> getNewActivities(long id, String username, Sort sort) {
		Specification<Activity> specification = idGreaterThan(id);
		if (username != null) {
			Users inDb = userService.getByUsername(username);
			specification = specification.and(userIs(inDb));
		}
		return activityRepository.findAll(specification, sort);
	}
	
	public List<Activity> findActivitiesByUserAttendedId(long id){
		return activityRepository.findActivitiesByUserAttendedId(id);
	}

	Specification<Activity> idLessThan(long id) {
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.lessThan(root.get("id"), id);
		};
	}

	Specification<Activity> userIs(Users user) {
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.equal(root.get("user"), user);
		};
	}

	Specification<Activity> idGreaterThan(long id) {
		return (root, query, criteriaBuilder) -> {
			return criteriaBuilder.greaterThan(root.get("id"), id);
		};
	}

	public void delete(long id) {
		Activity inDb = activityRepository.getOne(id);
		if (inDb.getFileAttachment() != null) {
			String fileName = inDb.getFileAttachment().getName();
			fileService.deleteAttachmentFile(fileName);
		}
		activityRepository.deleteById(id);
	}
}
