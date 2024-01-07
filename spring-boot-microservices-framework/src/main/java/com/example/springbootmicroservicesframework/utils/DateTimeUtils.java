package com.example.springbootmicroservicesframework.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

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

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.of(Const.DEFAULT_TIME_ZONE_ID))
                .toLocalDateTime();
    }

    public static LocalDateTime toLocalDateTime(Long time) {
        return new Timestamp(time).toLocalDateTime();
    }


}
