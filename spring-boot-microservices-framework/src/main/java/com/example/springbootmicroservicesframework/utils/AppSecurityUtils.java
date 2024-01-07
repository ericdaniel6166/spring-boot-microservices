package com.example.springbootmicroservicesframework.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppSecurityUtils {

    HttpServletRequest httpServletRequest;

    public String getAuthorizationHeader() {
        return httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    }

    public static boolean hasAnyRole(String... roles) {
        Collection<String> authorities = getAuthorities();
        for (var role: roles) {
            String roleWithPrefix = getRoleWithPrefix(SecurityConst.ROLE_PREFIX, role);
            if (authorities.contains(roleWithPrefix)) {
                return true;
            }
        }
        return false;
    }

    private static String getRoleWithPrefix(String prefix,String role) {
        return role.startsWith(prefix) ? role : prefix + role;
    }

    public static boolean hasRole(String role) {
        return hasAnyRole(role);
    }

    public static Collection<String> getAuthorities() {
        return getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    public static String getPreferredUsername () {
        return String.valueOf(getClaim(SecurityConst.PREFERRED_USERNAME));
    }

    public static String getUsername () {
        try {
            return getAuthentication().getName();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getEmail () {
        return String.valueOf(getClaim(SecurityConst.EMAIL));
    }

    public static Boolean getEmailVerified () {
        return (Boolean) getClaim(SecurityConst.EMAIL_VERIFIED);
    }

    public static String getScope () {
        return String.valueOf(getClaim(SecurityConst.SCOPE));
    }

    public static Object getClaim(String claim) {
        return getJwt().getClaim(claim);
    }

    public static Map<String, Object> getClaims() {
        return getJwt().getClaims();
    }

    private static Jwt getJwt() {
         return (Jwt) getAuthentication().getPrincipal();
    }

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
