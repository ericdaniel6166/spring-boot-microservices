package com.example.springbootmicroservicesframework.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class InternalServerException extends AppException {

    public InternalServerException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), message, null);
    }

    public InternalServerException(String message, List<ErrorDetail> errorDetails) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), message, errorDetails);
    }

}
