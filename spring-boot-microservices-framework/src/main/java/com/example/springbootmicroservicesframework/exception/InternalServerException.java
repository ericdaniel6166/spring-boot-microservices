package com.example.springbootmicroservicesframework.exception;

import org.springframework.http.HttpStatus;

public class InternalServerException extends AppException {

    public InternalServerException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.name(), message);
    }

}
