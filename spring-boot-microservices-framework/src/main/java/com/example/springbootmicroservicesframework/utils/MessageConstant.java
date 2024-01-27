package com.example.springbootmicroservicesframework.utils;

public final class MessageConstant {
    public static final String MSG_INF_RESOURCE_CREATED = "MSG_INF_RESOURCE_CREATED";

    public static final String MGS_RES_ACCOUNT = "MGS_RES_ACCOUNT";
    public static final String MGS_RES_USERNAME = "MGS_RES_USERNAME";
    public static final String MGS_RES_EMAIL = "MGS_RES_EMAIL";

    public static final String MSG_ERR_CONSTRAINS_VALID_VALUE = "constraints.ValidValue.message";
    public static final String MSG_ERR_CONSTRAINS_REQUIRED = "constraints.Required.message";

    public static final String MSG_ERR_RESOURCE_EXISTED = "MSG_ERR_RESOURCE_EXISTED";

    private MessageConstant() {
        throw new IllegalStateException("Utility class");
    }


}
