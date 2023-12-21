package com.example.springbootmicroservicesframework.integration.mqtt;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Configuration
@ConditionalOnProperty(name = "spring.mqtt.outbound.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "spring.mqtt.outbound")
public class MqttOutboundProperties {
    String username;
    String password;
    String[] serverURIs;
}
