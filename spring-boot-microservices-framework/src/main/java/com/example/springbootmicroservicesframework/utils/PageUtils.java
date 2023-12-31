package com.example.springbootmicroservicesframework.utils;

import com.example.springbootmicroservicesframework.dto.CursorPageResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Slf4j
public final class PageUtils {

    private PageUtils() {
        throw new IllegalStateException("Utility class");
    }


    public static Pageable buildPageable(Integer pageNumber, Integer size, Sort sort) {
        return PageRequest.of(pageNumber - 1, size, sort);
    }

    public static void addDefaultOrder(List<Sort.Order> orders) {
        if (orders.stream().anyMatch(order -> StringUtils.equals(order.getProperty(), Const.ID))) {
            return;
        }
        orders.add(Const.DEFAULT_SORT_ORDER);
    }

    public static <T> CursorPageResponse<T> buildBlankCursorPageResponse(Page<?> page) {
        return new CursorPageResponse<>(new ArrayList<>(), null, null, page);
    }

    public static String getDecodedCursor(String cursorValue) {
        if (cursorValue == null || cursorValue.isEmpty()) {
            throw new IllegalArgumentException("Cursor value is not valid!");
        }
        var decodedBytes = Base64.getDecoder().decode(cursorValue);
        var decodedValue = new String(decodedBytes);

        return StringUtils.substringBetween(decodedValue, "###");
    }

    public static String getEncodedCursor(String field) {
        Objects.requireNonNull(field);

        var structuredValue = "###" + field + "### - " + LocalDateTime.now();
        return Base64.getEncoder().encodeToString(structuredValue.getBytes());
    }
}
