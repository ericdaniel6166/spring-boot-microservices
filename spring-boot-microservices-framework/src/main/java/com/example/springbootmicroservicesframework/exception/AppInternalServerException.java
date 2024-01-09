package com.example.springbootmicroservicesframework.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

public class AppInternalServerException extends AppException {

    public AppInternalServerException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), message, null);
    }

    public AppInternalServerException(String message, List<ErrorDetail> errorDetails) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), message, errorDetails);
    }

}
