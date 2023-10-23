package com.example.springbootmicroservicesframework.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

@Slf4j
public class GlobalErrorHandler implements CommonErrorHandler {

    @Override
    public boolean handleOne(Exception thrownException, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer, MessageListenerContainer container) {
        log.info("Global error handler for message: {}", record.value().toString());
        return CommonErrorHandler.super.handleOne(thrownException, record, consumer, container);
    }

    @Override
    public void handleOtherException(Exception thrownException, Consumer<?, ?> consumer, MessageListenerContainer container, boolean batchListener) {
        log.info("Global error handler for consumer: {}", consumer);
        CommonErrorHandler.super.handleOtherException(thrownException, consumer, container, batchListener);
    }
}
