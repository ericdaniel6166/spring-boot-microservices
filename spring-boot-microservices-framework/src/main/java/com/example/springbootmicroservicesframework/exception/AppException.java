package com.example.springbootmicroservicesframework.exception;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends Exception {

    String error;

    HttpStatus httpStatus;

    int status;

    List<ErrorDetail> errorDetails;

    public AppException(String error, String message) {
        super(message);
        this.error = error;
    }

    public AppException(HttpStatus httpStatus, String error, String message, List<ErrorDetail> errorDetails) {
        super(message);
        this.error = error;
        this.errorDetails = errorDetails;
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
    }

}
