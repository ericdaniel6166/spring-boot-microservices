package com.example.springbootmicroservicesframework.config.pagination;

import com.example.springbootmicroservicesframework.utils.Const;
import com.example.springbootmicroservicesframework.validation.ValidEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CursorPageRequest {

    @Min(value = Const.DEFAULT_PAGE_SIZE)
    @Max(value = Const.MAXIMUM_PAGE_SIZE)
    Integer pageSize = Const.DEFAULT_PAGE_SIZE;

    String sortColumn = Const.ID;

    @ValidEnum(enumClass = Sort.Direction.class, caseSensitive = false)
    String sortDirection = Sort.Direction.ASC.name();

    String nextPageCursor;
    String prevPageCursor;

    public boolean hasNextPageCursor() {
        return nextPageCursor != null && !nextPageCursor.isEmpty();
    }

    public boolean hasPrevPageCursor() {
        return prevPageCursor != null && !prevPageCursor.isEmpty();
    }

    public boolean hasCursors() {
        return hasPrevPageCursor() || hasNextPageCursor();
    }

    public String getSearchValue() {
        if (!hasCursors()) return StringUtils.EMPTY;

        return hasPrevPageCursor()
                ? PageUtils.getDecodedCursor(prevPageCursor)
                : PageUtils.getDecodedCursor(nextPageCursor);
    }


}
