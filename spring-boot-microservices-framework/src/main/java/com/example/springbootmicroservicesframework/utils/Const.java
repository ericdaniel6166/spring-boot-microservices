package com.example.springbootmicroservicesframework.utils;

public final class Const {
    public static final String REQUEST_PARAM_LANGUAGE = "lang";
    public static final long MAXIMUM_PAGE_SIZE = 200;
    public static final int MAXIMUM_SORT_COLUMN = 10;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final long LONG_ZERO = 0L;
    public static final long LONG_ONE = 1L;
    public static final int INTEGER_ZERO = 0;
    public static final int INTEGER_ONE = 1;
    public static final String PLACEHOLDER_0 = "{0}";
    public static final String COMMON = "common";
    public static final String ID = "id";
    public static final String GENERAL_FIELD = "common.generalField";

    private Const() {
        throw new IllegalStateException("Utility class");
    }
}
