package com.example.springbootmicroservicesframework.validation;

import com.example.springbootmicroservicesframework.utils.Const;
import com.example.springbootmicroservicesframework.utils.MessageConstant;
import com.example.springbootmicroservicesframework.utils.ValidationUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    final ValidationUtils validationUtils;
    List<String> valueList;
    boolean caseSensitive;
    String message;
    String messageCode;
    String[] messageParams;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        valueList = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
        caseSensitive = constraintAnnotation.caseSensitive();
        message = constraintAnnotation.message();
        messageCode = constraintAnnotation.messageCode();
        messageParams = constraintAnnotation.messageParams();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(s)) {
            return true;
        }
        boolean isValid = valueList.contains(s);
        if (!caseSensitive) {
            isValid = valueList.contains(s.toUpperCase());
        }
        if (!isValid && StringUtils.isBlank(message)) {
            if (messageParams.length == 0 && StringUtils.isBlank(messageCode)) {
                validationUtils.addViolation(constraintValidatorContext,
                        MessageConstant.MSG_ERR_COMMON_TYPE_MISMATCH_ENUM,
                        new String[]{Const.PLACEHOLDER_0, valueList.toString()});
            } else {
                validationUtils.addViolation(constraintValidatorContext, messageCode, messageParams);
            }
        }
        return isValid;
    }

}
