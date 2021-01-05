package com.vadin.entity.viewmodel;

import com.vadin.entity.FileAttachment;

public class FileAttachmentViewModel {

	private String name;

	private String fileType;

	public FileAttachmentViewModel() {
		super();
	}

	public FileAttachmentViewModel(FileAttachment fileAttachment) {
		this.setName(fileAttachment.getName());
		this.fileType = fileAttachment.getFileType();
	}

	public FileAttachmentViewModel(String name, String fileType) {
		super();
		this.name = name;
		this.fileType = fileType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
