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
@ConditionalOnProperty(name = "spring.mqtt.inbound.enabled", havingValue = "true")
@ConfigurationProperties(prefix = "spring.mqtt.inbound")
public class MqttInboundProperties {
    String url;
    String[] topics;
    Long completionTimeout;
    Integer qos;

}
