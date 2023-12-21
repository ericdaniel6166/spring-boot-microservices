package com.example.springbootmicroservicesframework.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ValidationErrorDetail extends ErrorDetail {

    @JsonIgnore
    private String keyClass;

    private String field;

    @JsonIgnore
    private Object rejectedValue;

    private String message;

    public ValidationErrorDetail(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
