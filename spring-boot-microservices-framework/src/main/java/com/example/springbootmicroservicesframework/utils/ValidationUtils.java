package com.example.springbootmicroservicesframework.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValidationUtils {

    final MessageSource messageSource;

    public void addViolation(ConstraintValidatorContext constraintValidatorContext, String messageCode, String[] messageParams) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        String message = messageSource.getMessage(messageCode, messageParams, LocaleContextHolder.getLocale());
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
