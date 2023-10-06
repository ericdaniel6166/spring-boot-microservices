package com.example.springbootmicroservicesframework.pagination;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CursorPageResponse<T> {
    List<T> content;
    String prevPageCursor;
    String nextPageCursor;
    Long moreElements;
    Integer morePages;
    Integer size;
    Boolean last;

    public CursorPageResponse(List<T> content, String prevPageCursor, String nextPageCursor, Page<?> page) {
        this.content = content;
        this.prevPageCursor = prevPageCursor;
        this.nextPageCursor = nextPageCursor;
        long more = page.getTotalElements() - page.getSize();
        this.moreElements = more >= 0 ? more : 0;
        this.morePages = page.getTotalPages() - 1;
        this.size = page.getSize();
        this.last = page.isLast();
    }

}
