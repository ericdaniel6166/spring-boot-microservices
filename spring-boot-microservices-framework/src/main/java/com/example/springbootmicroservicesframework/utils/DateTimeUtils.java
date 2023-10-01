package com.example.springbootmicroservicesframework.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateTimeUtils {

    private DateTimeUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static LocalDateTime toLocalDateTime(String stringValue, DateTimeFormatter dateTimeFormatter) {
        LocalDateTime result;
        try {
            result = LocalDateTime.parse(stringValue, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
        return result;
    }
}
