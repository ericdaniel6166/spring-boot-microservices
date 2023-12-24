package com.example.springbootmicroservicesframework.validation;

import com.example.springbootmicroservicesframework.utils.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class StringValidator implements ConstraintValidator<ValidString, Collection<String>> {

    final ValidationUtils validationUtils;

    List<String> valueList;
    boolean caseSensitive;
    boolean checkNotBlank;
    String message;
    String messageCode;
    String[] messageParams;

    @Override
    public void initialize(ValidString constraintAnnotation) {
        caseSensitive = constraintAnnotation.caseSensitive();
        valueList = Arrays.asList(constraintAnnotation.values());
        message = constraintAnnotation.message();
        messageCode = constraintAnnotation.messageCode();
        messageParams = constraintAnnotation.messageParams();
        checkNotBlank = constraintAnnotation.checkNotBlank();
    }

    @Override
    public boolean isValid(Collection<String> s, ConstraintValidatorContext constraintValidatorContext) {
        if (CollectionUtils.isEmpty(s) && !checkNotBlank) {
            return true;
        }
        boolean isValid;
        if (caseSensitive) {
            isValid = valueList.containsAll(s);
        } else {
            isValid = valueList.containsAll(s.stream().map(String::toUpperCase).toList());
        }
        return validationUtils.handleConstrainsValidValue(constraintValidatorContext, isValid, message, messageParams, messageCode, valueList);
    }

}
