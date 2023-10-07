package com.example.springbootmicroservicesframework.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class BadRequestException extends AppException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), message, null);
    }

    public BadRequestException(String message, List<ErrorDetail> errorDetails) {
        super(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), message, errorDetails);
    }


}
