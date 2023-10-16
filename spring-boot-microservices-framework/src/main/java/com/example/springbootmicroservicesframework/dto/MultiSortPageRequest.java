package com.example.springbootmicroservicesframework.dto;

import com.example.springbootmicroservicesframework.utils.Const;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MultiSortPageRequest {

    @Min(value = Const.ONE)
    Integer pageNumber = Const.ONE;

    @Min(value = Const.DEFAULT_PAGE_SIZE)
    @Max(value = Const.MAXIMUM_PAGE_SIZE)
    Integer pageSize = Const.DEFAULT_PAGE_SIZE;

    @Valid
    List<AppSortOrder> orderList;
}
