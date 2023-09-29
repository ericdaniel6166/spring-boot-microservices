package com.example.springbootmicroservicesframework.validation;

import com.example.springbootmicroservicesframework.utils.Const;
import com.example.springbootmicroservicesframework.utils.MessageConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    final ValidationUtils validationUtils;
    List<String> valueList;
    boolean caseSensitive;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        valueList = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .toList();
        caseSensitive = constraintAnnotation.caseSensitive();
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
        if (!isValid) {
            validationUtils.addViolation(constraintValidatorContext,
                    MessageConstant.MSG_ERR_COMMON_TYPE_MISMATCH_ENUMS,
                    new String[]{Const.PLACEHOLDER_0, valueList.toString()});
        }
        return isValid;
    }

}
