package com.example.springbootmicroservicesframework.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

@Slf4j
public class GlobalErrorHandler implements CommonErrorHandler {

    @Override
    public void handleRecord(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        CommonErrorHandler.super.handleRecord(thrownException, record, consumer, container);
        log.warn("Global error handler for message: {}", record.value().toString());
    }

    @Override
    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        CommonErrorHandler.super.handleOtherException(thrownException, consumer, container, batchListener);
        log.warn("Global error handler for consumer: {}", consumer);
    }
}
