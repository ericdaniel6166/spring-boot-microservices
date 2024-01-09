package com.example.springbootmicroservicesframework.validation;

import com.example.springbootmicroservicesframework.dto.PasswordDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        PasswordDto password = (PasswordDto) obj;
        return StringUtils.equals(password.getPassword(), password.getConfirmPassword());
    }

}
