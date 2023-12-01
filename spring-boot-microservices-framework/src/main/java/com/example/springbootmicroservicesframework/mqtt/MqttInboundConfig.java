package com.example.springbootmicroservicesframework.mqtt;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "spring.mqtt.inbound.enabled", havingValue = "true")
public class MqttInboundConfig {

    public static final String CLIENT_ID = MqttAsyncClient.generateClientId();

    final MqttInboundProperties mqttInboundProperties;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(mqttInboundProperties.getUrl(), CLIENT_ID,
                        mqttInboundProperties.getTopics().toArray(new String[0]));
        adapter.setCompletionTimeout(mqttInboundProperties.getCompletionTimeout());
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(mqttInboundProperties.getQos());
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }


}
