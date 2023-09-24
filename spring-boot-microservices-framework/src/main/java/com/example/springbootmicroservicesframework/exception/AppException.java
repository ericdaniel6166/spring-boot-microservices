package com.example.springbootmicroservicesframework.exception;

public class AppException extends Exception {

    public AppException(String message) {
        super(message);
    }

    public AppException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "AppException: " + getMessage();
    }

}
