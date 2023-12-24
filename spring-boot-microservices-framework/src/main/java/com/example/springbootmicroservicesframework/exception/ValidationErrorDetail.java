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
    private String object;

    @JsonIgnore
    private Object rejectedValue;

    private String message;

    public ValidationErrorDetail(String field, String object, String message) {
        this.field = field;
        this.message = message;
        this.object = object;
    }
}
