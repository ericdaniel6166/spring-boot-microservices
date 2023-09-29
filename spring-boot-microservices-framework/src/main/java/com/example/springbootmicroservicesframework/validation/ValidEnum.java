package com.example.springbootmicroservicesframework.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {EnumValidator.class}
)
public @interface ValidEnum {

    Class<? extends Enum<?>> enumClass();

    boolean caseSensitive() default true;

    String message() default "{typeMismatch.enums}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
