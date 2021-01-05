package com.vadin.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vadin.entity.FileAttachment;
import com.vadin.service.FileService;

@RestController
public class FileController {
	
	@Autowired
	FileService fileService;

	@PostMapping("/api/activity-attachments")
	FileAttachment saveActivityAttachment(@RequestParam(name = "image") MultipartFile multipartFile) {
		return fileService.saveActivityAttachment(multipartFile);
	}
	
}
