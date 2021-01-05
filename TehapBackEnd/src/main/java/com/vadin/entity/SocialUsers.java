package com.vadin.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SocialUsers {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String providerId;
	
	private String username;

	private String email;

	private String displayName;

	private String provider;

	private String imageUrl;

	public SocialUsers() {
		super();
	}

	public SocialUsers(Long id, String providerId, String username, String email, String displayName, String provider,
			String imageUrl) {
		super();
		this.id = id;
		this.providerId = providerId;
		this.username = username;
		this.email = email;
		this.displayName = displayName;
		this.provider = provider;
		this.imageUrl = imageUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getproviderId() {
		return providerId;
	}

	public void setproviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}


}
