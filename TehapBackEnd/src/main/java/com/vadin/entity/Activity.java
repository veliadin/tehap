package com.vadin.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Activity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 1000)
	private String title;

	private String description;

	private String location;

	private Date startDate;

	private String activityHour;

	private String activityMinute;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@ManyToOne
	@JsonIgnore
	private Users user;

	@ManyToMany(cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Users> attendedUsers;

	@OneToOne(mappedBy = "activity", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private FileAttachment fileAttachment;

	public Activity() {
		super();
	}

	public Activity(long id, String title, String description, String location, Date startDate, String activityHour,
			String activityMinute, Date timestamp, Users user, List<Users> attendedUsers,
			FileAttachment fileAttachment) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.location = location;
		this.startDate = startDate;
		this.activityHour = activityHour;
		this.activityMinute = activityMinute;
		this.timestamp = timestamp;
		this.user = user;
		this.attendedUsers = attendedUsers;
		this.fileAttachment = fileAttachment;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public FileAttachment getFileAttachment() {
		return fileAttachment;
	}

	public void setFileAttachment(FileAttachment fileAttachment) {
		this.fileAttachment = fileAttachment;
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

	public List<Users> getAttendedUsers() {
		return attendedUsers;
	}

	public void setAttendedUsers(List<Users> attendedUsers) {
		this.attendedUsers = attendedUsers;
	}

}
