package com.vadin.shared;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.vadin.annotation.FileType;
import com.vadin.service.FileService;

public class FileTypeValidator implements ConstraintValidator<FileType, String> {
	
	@Autowired
	FileService fileService;
	
	String[] types;
	
	@Override
	public void initialize(FileType constraintAnnotation) {
		this.types = constraintAnnotation.types();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null || value.isEmpty()) {
			return true;
		}
		String fileType = fileService.detectType(value);
		
		for(String supportedType : this.types) {
			if(fileType.contains(supportedType)) {
				return true;
			}
		}
		String supportedTypes = Arrays.stream(this.types).collect(Collectors.joining(", "));
		
		//Kendi hata mesajımızı oluşturma/düzenleme. Amaç [jpeg, png] ifadesinde köşeli parantezleri kaldırma.
		context.disableDefaultConstraintViolation();
		HibernateConstraintValidatorContext hibernateConstraintValidatorContext =  context.unwrap(HibernateConstraintValidatorContext.class);
		hibernateConstraintValidatorContext.addMessageParameter("types", supportedTypes);
		hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
		
		return false;
	}

}
 