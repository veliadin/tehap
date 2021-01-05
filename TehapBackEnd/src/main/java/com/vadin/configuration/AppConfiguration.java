package com.vadin.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties // app propertiesdeki değişkenleri buraya assign et.
public class AppConfiguration {

	private String uploadPath;

	private String profileStorage = "profile";

	private String attachmentStorage = "attachments";

	public String getProfileStoragePath() {
		return uploadPath + "/" + profileStorage;
	}

	public String getAttachmentStoragePath() {
		return uploadPath + "/" + attachmentStorage;
	}

	public AppConfiguration() {
		super();
	}

	public AppConfiguration(String uploadPath) {
		super();
		this.uploadPath = uploadPath;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

}
