package com.example.springbootmicroservicesframework.utils;

import org.springframework.data.domain.Sort;

import java.time.format.DateTimeFormatter;

public final class Const {

    public static final String REQUEST_PARAM_LANGUAGE = "lang";
    public static final String REGEX_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final int MAXIMUM_PAGE_SIZE = 200;
    public static final int MAXIMUM_CREATE_UPDATE_MULTI_ITEM = 10;
    public static final int MAXIMUM_BIG_DECIMAL_INTEGER = 19;
    public static final int MAXIMUM_BIG_DECIMAL_FRACTION = 4;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final String DEFAULT_PAGE_SIZE_STRING = "10";
    public static final int MAXIMUM_SORT_COLUMN = 10;
    public static final int ZERO = 0;
    public static final int DEFAULT_PAGE_NUMBER = 1;
    public static final String DEFAULT_PAGE_NUMBER_STRING = "1";
    public static final int DEFAULT_SIZE_MAX_STRING = 255;
    public static final long DEFAULT_MAX_LONG = Long.MAX_VALUE; //change
    public static final int DEFAULT_MAX_INTEGER = Integer.MAX_VALUE; //change
    public static final String PLACEHOLDER_0 = "{0}";
    public static final String COMMON = "common";
    public static final String ID = "id";
    public static final String DEFAULT_SORT_COLUMN = "id";
    public static final String DEFAULT_SORT_DIRECTION = "ASC";
    public static final String GENERAL_FIELD = "common.generalField";
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME; //change
    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; //change
    public static final String DEFAULT_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"; //change
    public static final String DEFAULT_TIME_ZONE_ID = "UTC"; //change
    public static final Sort.Order DEFAULT_SORT_ORDER = new Sort.Order(Sort.Direction.ASC, ID);

    private Const() {
        throw new IllegalStateException("Utility class");
    }
}
