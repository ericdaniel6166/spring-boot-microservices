package com.example.springbootmicroservicesframework.utils;

import java.time.format.DateTimeFormatter;

public final class Const {
    public static final String REQUEST_PARAM_LANGUAGE = "lang";
    public static final int MAXIMUM_PAGE_SIZE = 200;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int MAXIMUM_SORT_COLUMN = 10;
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int DEFAULT_SIZE_MAX_STRING = 255;
    public static final long DEFAULT_MAX_NUMBER = Long.MAX_VALUE; //change
    public static final int DEFAULT_MAX_INTEGER = Integer.MAX_VALUE; //change
    public static final String PLACEHOLDER_0 = "{0}";
    public static final String COMMON = "common";
    public static final String ID = "id";
    public static final String GENERAL_FIELD = "common.generalField";
    public static final DateTimeFormatter DEFAULT_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME; //change


    private Const() {
        throw new IllegalStateException("Utility class");
    }
}
