package com.example.springbootmicroservicesframework.exception;

public class InternalServerException extends Exception {

    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return "InternalServerException: " + getMessage();
    }

}
