package com.vadin.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vadin.configuration.AppConfiguration;
import com.vadin.entity.FileAttachment;
import com.vadin.entity.Users;
import com.vadin.repository.FileAttachmentRepository;

@Service
@EnableScheduling
public class FileService {

	AppConfiguration appConfiguration;

	Tika tika;

	FileAttachmentRepository fileAttachmentRepository;

	public FileService(AppConfiguration appConfiguration, FileAttachmentRepository fileAttachmentRepository) {
		super();
		this.appConfiguration = appConfiguration;
		this.tika = new Tika();
		this.fileAttachmentRepository = fileAttachmentRepository;
	}

	public String writeBase64EncodedStringToFile(String image) throws IOException {

		String fileName = generateRandomName();
		File target = new File(appConfiguration.getProfileStoragePath() + "/" + fileName);
		OutputStream outputStream = new FileOutputStream(target);

		byte[] base64encoded = Base64.getDecoder().decode(image);

		outputStream.write(base64encoded);
		outputStream.close();
		return fileName;
	}

	public String generateRandomName() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public void deleteProfileImage(String oldImageName) {
		if (oldImageName == null) {
			return;
		}
		deleteFile(Paths.get(appConfiguration.getProfileStoragePath(), oldImageName));
	}

	public void deleteAttachmentFile(String oldImageName) {
		if (oldImageName == null) {
			return;
		}
		deleteFile(Paths.get(appConfiguration.getAttachmentStoragePath(), oldImageName));
	}

	private void deleteFile(Path path) {
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String detectType(String base64) {
		byte[] base64encoded = Base64.getDecoder().decode(base64);
		return detectType(base64encoded);
	}
	
	public String detectType(byte[] arr) {
		return tika.detect(arr);
	}

	public FileAttachment saveActivityAttachment(MultipartFile multipartFile) {
		String fileName = generateRandomName();
		String fileType = null;
		File target = new File(appConfiguration.getAttachmentStoragePath() + "/" + fileName);
		try {
			byte[] arr = multipartFile.getBytes();
			OutputStream outputStream = new FileOutputStream(target);
			outputStream.write(arr);
			outputStream.close();
			fileType = detectType(arr);
		} catch (IOException e) {
		}
		FileAttachment attachment = new FileAttachment();
		attachment.setName(fileName);
		attachment.setDate(new Date());
		attachment.setFileType(fileType);
		return fileAttachmentRepository.save(attachment);
	}

	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	public void cleanupStorage() {
		Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (1024 * 60 * 60 * 1000));
		List<FileAttachment> filesToBeDeleted = fileAttachmentRepository
				.findByDateBeforeAndActivityIsNull(twentyFourHoursAgo);
		for (FileAttachment file : filesToBeDeleted) {
			deleteAttachmentFile(file.getName());
			fileAttachmentRepository.deleteById(file.getId());
		}
	}

	public void deleteAllStoredFilesForUser(Users inDb) {
		deleteProfileImage(inDb.getImage());
		List<FileAttachment> filesToBeDeleted = fileAttachmentRepository.findByActivityUser(inDb);
		for(FileAttachment file : filesToBeDeleted) {
			deleteAttachmentFile(file.getName());
		}
	}

}
