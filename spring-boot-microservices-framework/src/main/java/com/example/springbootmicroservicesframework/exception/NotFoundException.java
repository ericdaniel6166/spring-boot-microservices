package com.example.springbootmicroservicesframework.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AppException {

    public NotFoundException(String resource) {
        super(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), String.format("%s not found", resource), null);
    }

}
