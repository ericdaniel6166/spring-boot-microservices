package com.example.springbootmicroservicesframework.config.security;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Configuration
@ConditionalOnProperty(name = "security.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "security")
public class SecurityProps {
    String[] skipUrls;
}
