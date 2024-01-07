package com.example.springbootmicroservicesframework.utils;

public final class SecurityConst {

    public static final String GROUP_CUSTOMER = "customer";

    public static final String ROLE_CUSTOMER = "CUSTOMER";
    public static final String ROLE_ADMIN = "ADMIN";

    public static final String PREFERRED_USERNAME = "preferred_username";
    public static final String EMAIL = "email";
    public static final String SCOPE = "scope";
    public static final String EMAIL_VERIFIED = "email_verified";
    public static final String REALM_ACCESS = "realm_access";

    public static final String ROLE_PREFIX = "ROLE_";

    public static final String ROLES = "roles";


    private SecurityConst() {
        throw new IllegalStateException("Utility class");
    }
}
