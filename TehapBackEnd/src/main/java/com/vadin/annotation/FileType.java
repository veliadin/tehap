package com.vadin.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.vadin.shared.FileTypeValidator;


@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileTypeValidator.class })
public @interface FileType {
	String message() default "{tehap.constraint.username.FileType.message}";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	String[] types();

}
