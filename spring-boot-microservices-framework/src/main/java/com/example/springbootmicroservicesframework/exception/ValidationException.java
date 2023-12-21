package com.example.springbootmicroservicesframework.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class ValidationException extends AppException {

    public ValidationException(List<ErrorDetail> errorDetails) {
        super(HttpStatus.BAD_REQUEST, ErrorCode.VALIDATION_ERROR.name(), null, errorDetails);
    }


}
