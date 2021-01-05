package com.vadin.entity.viewmodel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vadin.annotation.FileType;

public class UserUpdateViewModel {

	@NotNull
	@Size(min = 4, max = 255)
	private String displayName;

	@FileType(types = {"jpeg", "png"})
	private String image;

	public UserUpdateViewModel() {
		super();
	}

	public UserUpdateViewModel(String displayName, String image) {
		super();
		this.displayName = displayName;
		this.image = image;
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

}
