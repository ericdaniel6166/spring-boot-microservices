package com.example.springbootmicroservicesframework.utils;

import com.example.springbootmicroservicesframework.dto.MessageResponse;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import java.time.format.DateTimeFormatter;

public final class Const {


    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String STATUS = "status";
    public static final String REQUEST_ID = "requestId";
    public static final String TIMESTAMP = "timestamp";
    public static final String REQUEST_PARAM_LANGUAGE = "lang";
    public static final int MAXIMUM_PAGE_SIZE = 200;
    public static final int MAXIMUM_CREATE_UPDATE_MULTI_ITEM = 10;
    public static final int MAXIMUM_BIG_DECIMAL_INTEGER = 19;
    public static final int MAXIMUM_BIG_DECIMAL_FRACTION = 4;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_PAGE_SIZE_STRING = "10";
    public static final int MAXIMUM_SORT_COLUMN = 10;
    public static final int ZERO = 0;
    public static final int INTEGER_ONE = 1;
    public static final String STRING_ONE = "1";
    public static final int DEFAULT_SIZE_MAX_STRING = 255;
    public static final int SIZE_MAX_STRING = 60000;
    public static final long DEFAULT_MAX_LONG = Long.MAX_VALUE; //change
    public static final int DEFAULT_MAX_INTEGER = Integer.MAX_VALUE; //change
    public static final String PLACEHOLDER_0 = "{0}";
    public static final String COMMON = "common";
    public static final String ID = "id";
    public static final String DEFAULT_SORT_COLUMN = "id";
    public static final String DEFAULT_SORT_DIRECTION = "ASC";
    public static final String GENERAL_FIELD = "common.generalField";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_EMAIL = "email";
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME; //change
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; //change
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"; //change
    public static final String DEFAULT_TIME_ZONE_ID = "UTC"; //change
    public static final Sort.Order DEFAULT_SORT_ORDER = new Sort.Order(Sort.Direction.ASC, ID);
    public static final String ISSUER = "issuer";
    public static final String SCOPE = "scope";
    public static final String HEADER_AUTHORIZATION_PREFIX = "Bearer ";
    public static final MessageResponse MESSAGE_RESPONSE_OK = new MessageResponse(HttpStatus.OK.getReasonPhrase());
    public static final String TRACE_ID = "traceId";
    public static final String GROUP_CUSTOMER = "customer";

    private Const() {
        throw new IllegalStateException("Utility class");
    }
}
