package com.example.springbootmicroservicesframework.integration.feign;

import com.example.springbootmicroservicesframework.exception.AppException;
import com.example.springbootmicroservicesframework.exception.AppBadRequestException;
import com.example.springbootmicroservicesframework.exception.AppInternalServerException;
import com.example.springbootmicroservicesframework.exception.AppNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        // improvement later
        return switch (response.status()) {
            case HttpStatus.SC_BAD_REQUEST -> new AppBadRequestException("Bad Request !!!");
            case HttpStatus.SC_NOT_FOUND -> new AppNotFoundException("Not Found !!!");
            case HttpStatus.SC_INTERNAL_SERVER_ERROR -> new AppInternalServerException("Internal Server Error !!!");
            default -> new AppException("GENERIC_ERROR", "Generic error !!!");
        };
    }
}
