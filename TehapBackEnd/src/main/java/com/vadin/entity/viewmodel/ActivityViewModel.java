package com.vadin.entity.viewmodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.vadin.entity.Activity;
import com.vadin.entity.FileAttachment;
import com.vadin.entity.Users;

import lombok.Data;

@Data
public class ActivityViewModel {

	private long id;

	private String title;

	private long timestamp;

	private UserViewModel user;

	private FileAttachmentViewModel fileAttachmentViewModel;

	private String description;

	private String location;

	private Date startDate;

	private String activityHour;

	private String activityMinute;

	public ActivityViewModel() {
		super();
	}

	public ActivityViewModel(Activity activity) {
		this.setId(activity.getId());
		this.setTitle(activity.getTitle());
		this.setTimestamp(activity.getTimestamp().getTime());
		this.setUser(new UserViewModel(activity.getUser()));
		this.setActivityHour(activity.getActivityHour());
		this.setActivityMinute(activity.getActivityMinute());
		this.setDescription(activity.getDescription());
		this.setLocation(activity.getLocation());
		this.setStartDate(activity.getStartDate());
		if (activity.getFileAttachment() != null) {
			this.fileAttachmentViewModel = new FileAttachmentViewModel(activity.getFileAttachment());

		}
	}

	public ActivityViewModel(long id, String title, long timestamp, UserViewModel user,
			FileAttachmentViewModel fileAttachmentViewModel, String description, String location, Date startDate,
			String activityHour, String activityMinute) {
		super();
		this.id = id;
		this.title = title;
		this.timestamp = timestamp;
		this.user = user;
		this.fileAttachmentViewModel = fileAttachmentViewModel;
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.activityHour = activityHour;
		this.activityMinute = activityMinute;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public UserViewModel getUser() {
		return user;
	}

	public void setUser(UserViewModel user) {
		this.user = user;
	}

	public FileAttachmentViewModel getFileAttachmentViewModel() {
		return fileAttachmentViewModel;
	}

	public void setFileAttachmentViewModel(FileAttachmentViewModel fileAttachmentViewModel) {
		this.fileAttachmentViewModel = fileAttachmentViewModel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getActivityHour() {
		return activityHour;
	}

	public void setActivityHour(String activityHour) {
		this.activityHour = activityHour;
	}

	public String getActivityMinute() {
		return activityMinute;
	}

	public void setActivityMinute(String activityMinute) {
		this.activityMinute = activityMinute;
	}

}
