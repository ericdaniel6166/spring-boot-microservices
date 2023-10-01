package com.example.springbootmicroservicesframework.utils;

import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public final class AppReflectionUtils {

    private AppReflectionUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static <T> Object getField(Class<T> clazz, String fieldName, T object) throws IllegalAccessException {
        Field field = ReflectionUtils.findField(clazz, fieldName);
        Assert.notNull(field, "field must not be null");
        ReflectionUtils.makeAccessible(field);
        return field.get(object);
    }
}
