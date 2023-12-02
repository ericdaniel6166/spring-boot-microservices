package com.example.springbootmicroservicesframework.config.flyway;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Configuration
@ConditionalOnProperty(name = "spring.flyway.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "spring.flyway")
public class FlywayProperties {

    String baselineVersion;

    String locations;

    Boolean validateOnMigrate;

    String table;

    Boolean baselineOnMigrate;

}
