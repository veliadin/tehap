package com.vadin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vadin.entity.FileAttachment;
import com.vadin.entity.Users;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long> {

	List<FileAttachment> findByDateBeforeAndActivityIsNull(Date date);
	
	List<FileAttachment> findByActivityUser(Users user);
}
