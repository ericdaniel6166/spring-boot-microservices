package com.example.springbootmicroservicesframework.utils;

import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;


@RequiredArgsConstructor
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ValidationUtils {

    MessageUtils messageUtils;

    public boolean handleConstrainsValidValue(ConstraintValidatorContext constraintValidatorContext, boolean isValid, String message, String[] messageParams, String messageCode, List<String> valueList) {
        if (!isValid && StringUtils.isBlank(message)) {
            if (messageParams.length == 0 && StringUtils.isBlank(messageCode)) {
                messageUtils.addViolation(constraintValidatorContext,
                        MessageConstant.MSG_ERR_CONSTRAINS_VALID_VALUE,
                        new String[]{Const.PLACEHOLDER_0, valueList.toString()});
            } else {
                messageUtils.addViolation(constraintValidatorContext, messageCode, messageParams);
            }
        }
        return isValid;
    }
}
