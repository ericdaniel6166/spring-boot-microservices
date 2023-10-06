package com.example.springbootmicroservicesframework.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 123423L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime timestamp;

    private int status;

    @JsonIgnore
    private HttpStatus httpStatus;

    private String traceId;

    private String error;

    private String message;

    private String path;

    private List<ErrorDetail> errorDetails;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(HttpStatus httpStatus, String error, String message, HttpServletRequest httpServletRequest, List<ErrorDetail> errorDetails) {
        this();
        this.httpStatus = httpStatus;
        this.status = httpStatus.value();
        this.error = error;
        this.message = message;
        this.path = httpServletRequest.getRequestURI();
        this.errorDetails = errorDetails;
    }


}
