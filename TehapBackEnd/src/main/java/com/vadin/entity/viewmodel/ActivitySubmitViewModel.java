package com.vadin.entity.viewmodel;

import java.util.Date;

import javax.validation.constraints.Size;

public class ActivitySubmitViewModel {

	@Size(min = 1, max = 100)
	private String title;

	private String description;

	private String location;

	private Date startDate;

	private String activityHour;

	private String activityMinute;

	private long attachmentId;

	public ActivitySubmitViewModel() {
		super();
	}

	public ActivitySubmitViewModel(@Size(min = 1, max = 100) String title, String description, String location,
			Date startDate, String activityHour, String activityMinute, long attachmentId) {
		super();
		this.title = title;
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.activityHour = activityHour;
		this.activityMinute = activityMinute;
		this.attachmentId = attachmentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public long getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(long attachmentId) {
		this.attachmentId = attachmentId;
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
