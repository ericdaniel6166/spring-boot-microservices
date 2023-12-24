package com.example.springbootmicroservicesframework.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppSecurityUtils {

    HttpServletRequest httpServletRequest;

    public String getAccessToken() {
        return extractToken(getAuthorizationHeader());
    }

    public String getAuthorizationHeader() {
        return httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    }

    private static String extractToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(Const.HEADER_AUTHORIZATION_PREFIX)) {
            return authorizationHeader.substring(Const.HEADER_AUTHORIZATION_PREFIX.length());
        }
        return null;
    }
}
