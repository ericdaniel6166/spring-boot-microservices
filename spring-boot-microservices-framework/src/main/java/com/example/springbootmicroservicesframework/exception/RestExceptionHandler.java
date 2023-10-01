package com.example.springbootmicroservicesframework.exception;

import com.example.springbootmicroservicesframework.utils.Const;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RestExceptionHandler {

    final MessageSource messageSource;

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

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException e, HttpServletRequest httpServletRequest, HandlerMethod handlerMethod) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR.name(), null, httpServletRequest, null);

        List<ErrorDetail> errorDetails = e.getBindingResult().getAllErrors().stream()
                .filter(FieldError.class::isInstance)
                .map(error -> mapFieldErrorToErrorDetail((FieldError) error, handlerMethod.getBeanType().getSimpleName()))
                .toList();
        errorResponse.setErrorDetails(errorDetails);

        return buildResponseExceptionEntity(errorResponse);
    }

    private ErrorDetail mapFieldErrorToErrorDetail(FieldError fieldError, String apiClassName) {
        String keyClass = String.format("%s.%s.%s", apiClassName, fieldError.getObjectName(), fieldError.getField());
        String keyField = String.format("%s.%s", Const.COMMON, fieldError.getField());
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
        return new ValidationErrorDetail(fieldError.getObjectName(), fieldError.getField(),
                fieldError.getRejectedValue(), StringUtils.capitalize(msg));
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

    private ResponseEntity<Object> buildResponseExceptionEntity(ErrorResponse errorResponse) {
//    if (operationIdConfiguration != null) {
//      errorResponse.setOperationId(operationIdConfiguration.getOperationId());
//    }

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

}
