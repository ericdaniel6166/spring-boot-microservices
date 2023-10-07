package com.example.springbootmicroservicesframework.config.tracing;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@ConditionalOnClass(value = Tracer.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SleuthTraceIdContext implements TraceIdContext {

    final Tracer tracer;

    @Override
    public String getTraceId() {
        var span = Objects.requireNonNull(tracer.currentSpan());
        return span.context().traceId();
    }
}
