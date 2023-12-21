package com.example.springbootmicroservicesframework.integration.feign;

import com.example.springbootmicroservicesframework.exception.AppException;
import com.example.springbootmicroservicesframework.exception.BadRequestException;
import com.example.springbootmicroservicesframework.exception.InternalServerException;
import com.example.springbootmicroservicesframework.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.http.HttpStatus;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        // improvement later
        return switch (response.status()) {
            case HttpStatus.SC_BAD_REQUEST -> new BadRequestException("Bad Request !!!");
            case HttpStatus.SC_NOT_FOUND -> new NotFoundException("Not Found !!!");
            case HttpStatus.SC_INTERNAL_SERVER_ERROR -> new InternalServerException("Internal Server Error !!!");
            default -> new AppException("GENERIC_ERROR", "Generic error !!!");
        };
    }
}
