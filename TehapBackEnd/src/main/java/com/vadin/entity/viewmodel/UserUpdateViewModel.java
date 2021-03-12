package com.vadin.entity.viewmodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vadin.annotation.FileType;

public class UserUpdateViewModel {

	@NotNull
	@Size(min = 4, max = 255)
	private String displayName;

	private String email;

	private String university;

	private String branch;

	@FileType(types = { "jpeg", "png" })
	private String image;

	public UserUpdateViewModel() {
		super();
	}

	public UserUpdateViewModel(
			@NotNull @Size(min = 4, max = 255) String displayName, 
			String email, 
			String university,
			String branch, 
			String image) 
	{
		super();
		this.displayName = displayName;
		this.email = email;
		this.university = university;
		this.branch = branch;
		this.image = image;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
