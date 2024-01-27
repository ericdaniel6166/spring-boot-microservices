package com.example.springbootmicroservicesframework.exception;

import com.example.springbootmicroservicesframework.config.tracing.AppTraceIdContext;
import com.example.springbootmicroservicesframework.utils.Const;
import com.example.springbootmicroservicesframework.utils.MessageConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RestExceptionHandler {

    private static final String KEY_TEMPLATE = "%s.%s";
    private static final String KEY_CLASS_TEMPLATE = "%s.%s.%s";


    MessageSource messageSource;
    AppTraceIdContext appTraceIdContext;

    private static String getRootCauseMessage(Exception e) {
        return ExceptionUtils.getRootCause(e).getMessage();
    }

    @ExceptionHandler(AppNotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(AppNotFoundException e, HttpServletRequest httpServletRequest) {
        var errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, e.getError(),
                e.getMessage(), httpServletRequest, null);

        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler(AppValidationException.class)
    public ResponseEntity<Object> handleValidationException(AppValidationException e, HttpServletRequest httpServletRequest) {
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getError(),
                e.getMessage(), httpServletRequest, e.getErrorDetails());
        return buildResponseExceptionEntity(errorResponse);
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e, HttpServletRequest httpServletRequest) {
//        String errorMessage = getRootCauseMessage(e);
//        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.name(),
//                errorMessage, httpServletRequest, null);
//        return buildResponseExceptionEntity(errorResponse);
//    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest httpServletRequest) {
        var errorMessage = getRootCauseMessage(e);
        var errorResponse = new ErrorResponse(HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.name(),
                errorMessage, httpServletRequest, null);
        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest httpServletRequest,
                                                                     HandlerMethod handlerMethod) {

        var errorDetails = e.getConstraintViolations().stream()
                .map(constraintViolation -> mapConstrainViolationToErrorDetail(constraintViolation, handlerMethod.getBeanType().getSimpleName()))
                .toList();
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR.name(), null, httpServletRequest, errorDetails);

        return buildResponseExceptionEntity(errorResponse);
    }

    private ErrorDetail mapConstrainViolationToErrorDetail(ConstraintViolation<?> constraintViolation, String apiClassName) {
        var propertyPath = constraintViolation.getPropertyPath().toString();
        var keyClass = String.format(KEY_TEMPLATE, apiClassName, propertyPath);
        var parts = propertyPath.split("\\.");
        Assert.isTrue(parts.length > 0, "parts length must be greater than 0");
        var field = parts[parts.length - 1];
        var keyField = String.format(KEY_TEMPLATE, Const.COMMON, field);
        var model = getModel(keyClass, keyField);
        var msg = constraintViolation.getMessage();
        if (constraintViolation.getMessage().contains(Const.PLACEHOLDER_0)) {
            var messageFormat = new MessageFormat(constraintViolation.getMessage());
            msg = messageFormat.format(new Object[]{model});
        }
        return new ValidationErrorDetail(keyClass, field, null,
                constraintViolation.getInvalidValue(), StringUtils.capitalize(msg));
    }

    private ErrorDetail mapFieldErrorToErrorDetail(FieldError fieldError, String apiClassName) {
        var keyClass = String.format(KEY_CLASS_TEMPLATE, apiClassName, fieldError.getObjectName(), fieldError.getField());
        var keyField = String.format(KEY_TEMPLATE, Const.COMMON, fieldError.getField());
        var model = getModel(keyClass, keyField);
        String messageTemplate = null;
        if (ObjectUtils.isNotEmpty(fieldError.getCodes())) {
            for (String errorCode : fieldError.getCodes()) {
                try {
                    messageTemplate = messageSource.getMessage(errorCode, null, LocaleContextHolder.getLocale());
                    break;
                } catch (NoSuchMessageException ignored) {
                    //
                }
            }
        }
        if (StringUtils.isBlank(messageTemplate)) {
            messageTemplate = fieldError.getDefaultMessage();
        }
        Assert.isTrue(messageTemplate != null, "messageTemplate is not null");
        var msg = messageTemplate;
        if (messageTemplate.contains(Const.PLACEHOLDER_0)) {
            var messageFormat = new MessageFormat(messageTemplate);
            msg = messageFormat.format(new Object[]{model});
        }
        return new ValidationErrorDetail(keyClass, fieldError.getField(), null,
                fieldError.getRejectedValue(), StringUtils.capitalize(msg));
    }

    private ErrorDetail mapMethodArgumentTypeMismatchExceptionToErrorDetail(String apiClassName, MethodArgumentTypeMismatchException e, String methodName) {
        var parameterName = e.getParameter().getParameterName();
        var keyClass = String.format(KEY_CLASS_TEMPLATE, apiClassName, methodName, parameterName);
        var keyField = String.format(KEY_TEMPLATE, Const.COMMON, parameterName);
        var model = getModel(keyClass, keyField);
        var requiredType = e.getRequiredType();
        Assert.isTrue( requiredType != null, "requiredType is not null");
        var errorCode = String.format(KEY_TEMPLATE, e.getErrorCode(), requiredType.getName());
        var message = getErrorMessage(model, errorCode, e.getMessage());
        return new ValidationErrorDetail(keyClass, parameterName, null,
                e.getValue(), StringUtils.capitalize(message));
    }

    private ErrorDetail mapMissingServletRequestParameterExceptionToErrorDetail(String apiClassName, MissingServletRequestParameterException e, String methodName) {
        var parameterName = e.getParameterName();
        var keyClass = String.format(KEY_CLASS_TEMPLATE, apiClassName, methodName, parameterName);
        var keyField = String.format(KEY_TEMPLATE, Const.COMMON, parameterName);
        var model = getModel(keyClass, keyField);
        var message = getErrorMessage(model, MessageConstant.MSG_ERR_CONSTRAINS_REQUIRED, e.getMessage());
        return new ValidationErrorDetail(keyClass, parameterName, null,
                null, StringUtils.capitalize(message));
    }

    private String getErrorMessage(String model, String errorCode, String message) {
        String messageTemplate = null;
        try {
            messageTemplate = messageSource.getMessage(errorCode, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException ignored) {
            //
        }
        if (StringUtils.isBlank(messageTemplate)) {
            messageTemplate = message;
        }
        var messageFormat = new MessageFormat(messageTemplate);
        return messageFormat.format(new Object[]{model});
    }


    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleBindException(BindException e, HttpServletRequest httpServletRequest, HandlerMethod handlerMethod) {
        var errorDetails = e.getBindingResult().getAllErrors().stream()
                .map(error -> mapToErrorDetail(handlerMethod, error))
                .toList();
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR.name(), null, httpServletRequest, errorDetails);
        return buildResponseExceptionEntity(errorResponse);
    }

    private ErrorDetail mapToErrorDetail(HandlerMethod handlerMethod, ObjectError error) {
        if (error instanceof FieldError fieldError) {
            return mapFieldErrorToErrorDetail(fieldError, handlerMethod.getBeanType().getSimpleName());
        }
        return mapObjectErrorToErrorDetail(error, handlerMethod.getBeanType().getSimpleName(),
                handlerMethod.getMethod().getName());
    }

    private ErrorDetail mapObjectErrorToErrorDetail(ObjectError objectError, String apiClassName, String methodName) {
        var keyClass = String.format(KEY_CLASS_TEMPLATE, apiClassName, methodName, objectError.getObjectName());
        return new ValidationErrorDetail(keyClass, null, objectError.getObjectName(), null,
                StringUtils.capitalize(objectError.getDefaultMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest httpServletRequest, HandlerMethod handlerMethod) {
        var errorDetails = Collections.singletonList(mapMethodArgumentTypeMismatchExceptionToErrorDetail(
                handlerMethod.getBeanType().getSimpleName(), e, handlerMethod.getMethod().getName()));
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR.name(), null, httpServletRequest, errorDetails);
        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest httpServletRequest, HandlerMethod handlerMethod) {
        var errorDetails = Collections.singletonList(mapMissingServletRequestParameterExceptionToErrorDetail(
                handlerMethod.getBeanType().getSimpleName(), e, handlerMethod.getMethod().getName()));
        var errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST,
                ErrorCode.VALIDATION_ERROR.name(), null, httpServletRequest, errorDetails);
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
                //
            }
        }
        if (StringUtils.isBlank(model)) {
//            model = keyClass; //delete //for local testing
            model = messageSource.getMessage(Const.GENERAL_FIELD, null, LocaleContextHolder.getLocale()); //uncomment
        }
        return model;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        ErrorResponse errorResponse = new ErrorResponse((HttpStatus) e.getStatusCode(), ((HttpStatus) e.getStatusCode()).name(),
                errorMessage, httpServletRequest, null);
        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<Object> handleAppException(AppException e, HttpServletRequest httpServletRequest) {
        ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getError(),
                e.getMessage(), httpServletRequest, e.getErrorDetails());
        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        log.info(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), httpServletRequest, null);
        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class,
            ServletRequestBindingException.class,
            InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> handleBadRequestException(Exception e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        log.info(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(), httpServletRequest, null);
        return buildResponseExceptionEntity(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotAllowedException(Exception e, HttpServletRequest httpServletRequest) {
        String errorMessage = getRootCauseMessage(e);
        log.info(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.METHOD_NOT_ALLOWED, HttpStatus.METHOD_NOT_ALLOWED.name(),
                HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), httpServletRequest, null);
        return buildResponseExceptionEntity(errorResponse);
    }

    private ResponseEntity<Object> buildResponseExceptionEntity(ErrorResponse errorResponse) {
        if (ObjectUtils.isNotEmpty(appTraceIdContext)) {
            errorResponse.setTraceId(appTraceIdContext.getTraceId());
        }
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

}
