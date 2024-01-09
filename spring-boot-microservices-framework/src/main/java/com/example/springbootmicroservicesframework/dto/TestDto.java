package com.example.springbootmicroservicesframework.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDto {
    String username;
    String email;
    Boolean emailVerified;
    String scope;
    String fullName;
    String firstName;
    String lastName;
    String issuer;
    String jwtId;
    String remoteAddress;
    String sessionId;
    String subject;
    String tokenValue;
    List<String> audience;
    LocalDateTime expiresAt;
    LocalDateTime issuedAt;
    LocalDateTime notBefore;
    Collection<String> authorities;
    Map<String, Object> claims;
}
