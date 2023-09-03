package com.example.springbootmicroservicesframework.config;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class AppKafkaProperties {

    @Value("${spring.kafka.bootstrap-servers:localhost:9092}")
    String bootstrapServers;

    @Value(value = "${spring.kafka.consumer.group-id}:notificationId")
    String groupId;

}
