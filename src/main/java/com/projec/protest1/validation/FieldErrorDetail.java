package com.projec.protest1.validation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import java.util.Locale;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FieldErrorDetail {
    private String objectName;
    private String field;
    private String code;
    private String message;

    public static FieldErrorDetail create(FieldError fieldError, MessageSource messageSource, Locale locale) {
        return new FieldErrorDetail(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getCode(),
                messageSource.getMessage(fieldError, locale));
    }
}
