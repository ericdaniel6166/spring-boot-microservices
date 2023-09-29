package com.example.springbootmicroservicesframework.pagination;

import com.example.springbootmicroservicesframework.utils.Const;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MultiSortPageRequest {

    @Min(value = Const.LONG_ONE)
    Integer pageNumber = Const.INTEGER_ZERO;

    @Min(value = Const.DEFAULT_PAGE_SIZE)
    @Max(value = Const.MAXIMUM_PAGE_SIZE)
    Integer pageSize = Const.DEFAULT_PAGE_SIZE;

    @Valid
    List<AppSortOrder> orderList;
}
