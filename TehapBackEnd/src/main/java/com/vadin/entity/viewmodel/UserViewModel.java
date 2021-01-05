package com.vadin.entity.viewmodel;

import com.vadin.entity.Users;

public class UserViewModel {

	private String username;

	private String displayName;

	private String image;

	private String email;

	private String usersurname;

	private String university;

	private String branch;

	public UserViewModel() {
		super();
	}

	public UserViewModel(String username, String displayName, String image, String email, String usersurname,
			String university, String branch) {
		super();
		this.username = username;
		this.displayName = displayName;
		this.image = image;
		this.email = email;
		this.usersurname = usersurname;
		this.university = university;
		this.branch = branch;
	}

	public UserViewModel(Users user) {
		this.setUsername(user.getUsername());
		this.setDisplayName(user.getDisplayName());
		this.setImage(user.getImage());
		this.setEmail(user.getEmail());
		this.setUsersurname(user.getUsersurname());
		this.setUniversity(user.getUniversity());
		this.setBranch(user.getBranch());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsersurname() {
		return usersurname;
	}

	public void setUsersurname(String usersurname) {
		this.usersurname = usersurname;
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

}
