package com.example.springbootmicroservicesframework.exception;

import com.example.springbootmicroservicesframework.tracing.TraceIdContext;
import com.example.springbootmicroservicesframework.utils.Const;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RestExceptionHandler {

    static final String KEY_FIELD_TEMPLATE = "%s.%s";
    static final String KEY_CLASS_TEMPLATE = "%s.%s.%s";

    final MessageSource messageSource;
    final TraceIdContext traceIdContext;

    private static String getRootCauseMessage(Exception e) {
        return ExceptionUtils.getRootCause(e).getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getError(),
                errorMessage, httpServletRequest, e.getErrorDetails());

        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest httpServletRequest, HandlerMethod handlerMethod) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR.name(), null, httpServletRequest, null);
        List<ErrorDetail> errorDetails = e.getConstraintViolations().stream()
                .map(constraintViolation -> mapToErrorDetail(constraintViolation, handlerMethod.getBeanType().getSimpleName()))
                .toList();
        errorResponse.setErrorDetails(errorDetails);

        return buildResponseExceptionEntity(errorResponse);
    }

    private ErrorDetail mapToErrorDetail(ConstraintViolation<?> constraintViolation, String apiClassName) {
        String propertyPath = constraintViolation.getPropertyPath().toString();
        String keyClass = String.format(KEY_FIELD_TEMPLATE, apiClassName, propertyPath);
        String[] parts = propertyPath.split("\\.");
        Assert.isTrue(parts.length > 1, "parts length must be greater than 1");
        String field = parts[parts.length - 1];
        String keyField = String.format(KEY_FIELD_TEMPLATE, Const.COMMON, field);
        Assert.notNull(keyField, "keyField must be not null");
        String model = getModel(keyClass, keyField);
        MessageFormat messageFormat = new MessageFormat(constraintViolation.getMessage());
        String msg = messageFormat.format(new Object[]{model});
        return new ValidationErrorDetail(keyClass, field,
                constraintViolation.getInvalidValue(), StringUtils.capitalize(msg));
    }

    private ErrorDetail mapToErrorDetail(FieldError fieldError, String apiClassName) {
        String keyClass = String.format(KEY_CLASS_TEMPLATE, apiClassName, fieldError.getObjectName(), fieldError.getField());
        String keyField = String.format(KEY_FIELD_TEMPLATE, Const.COMMON, fieldError.getField());
        String model = getModel(keyClass, keyField);
        String messageTemplate = null;
        for (String errorCode : fieldError.getCodes()) {
            try {
                messageTemplate = messageSource.getMessage(errorCode, null, LocaleContextHolder.getLocale());
                break;
            } catch (NoSuchMessageException ignored) {
            }
        }
        if (StringUtils.isBlank(messageTemplate)) {
            messageTemplate = fieldError.getDefaultMessage();
        }

        MessageFormat messageFormat = new MessageFormat(messageTemplate);
        String msg = messageFormat.format(new Object[]{model});
        return new ValidationErrorDetail(keyClass, fieldError.getField(),
                fieldError.getRejectedValue(), StringUtils.capitalize(msg));
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException e, HttpServletRequest httpServletRequest, HandlerMethod handlerMethod) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR.name(), null, httpServletRequest, null);

        List<ErrorDetail> errorDetails = e.getBindingResult().getAllErrors().stream()
                .filter(FieldError.class::isInstance)
                .map(error -> mapToErrorDetail((FieldError) error, handlerMethod.getBeanType().getSimpleName()))
                .toList();
        errorResponse.setErrorDetails(errorDetails);

        return buildResponseExceptionEntity(errorResponse);
    }


    private String getModel(String keyClass, String keyField) {
        List<String> keyList = Arrays.asList(keyClass, keyField);
        String model = null;
        for (String key : keyList) {
            try {
                model = messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
                break;
            } catch (NoSuchMessageException ignored) {
            }
        }
        if (StringUtils.isBlank(model)) {
            model = keyClass; //delete //for local testing
//            model = messageSource.getMessage(Const.GENERAL_FIELD, null, LocaleContextHolder.getLocale());
        }
        return model;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        ErrorResponse errorResponse = new ErrorResponse(e.getStatus(), e.getStatus().name(),
                errorMessage, httpServletRequest, null);
        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> handleAppException(AppException e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getError(),
                errorMessage, httpServletRequest, e.getErrorDetails());
        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(),
                errorMessage, httpServletRequest, null);
        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> handleBadRequestException(Exception e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        log.info(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), httpServletRequest, null);
        return buildResponseExceptionEntity(errorResponse);
    }

    private ResponseEntity<Object> buildResponseExceptionEntity(ErrorResponse errorResponse) {
        if (ObjectUtils.isNotEmpty(traceIdContext)) {
            errorResponse.setTraceId(traceIdContext.getTraceId());
        }
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

}
