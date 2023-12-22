package com.example.springbootmicroservicesframework.utils;

public class UriConst {

    public static final String USER_SERVICE_URI = "lb://user-service";
    public static final String USER_SERVICE_CONTEXT_PATH = "/api/user";
    public static final String AUTH = "/auth";
    public static final String VERIFY_TOKEN = "/verify-token";

    public static final String URI_VERIFY_TOKEN = UriConst.USER_SERVICE_URI +
            UriConst.USER_SERVICE_CONTEXT_PATH +
            UriConst.AUTH +
            UriConst.VERIFY_TOKEN;

    private UriConst() {
        throw new IllegalStateException("Utility class");
    }
}
