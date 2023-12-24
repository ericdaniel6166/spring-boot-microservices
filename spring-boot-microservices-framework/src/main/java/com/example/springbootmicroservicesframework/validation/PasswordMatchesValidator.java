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
        PasswordDto account = (PasswordDto) obj;
        return StringUtils.equals(account.getPassword(), account.getConfirmPassword());
    }

}
