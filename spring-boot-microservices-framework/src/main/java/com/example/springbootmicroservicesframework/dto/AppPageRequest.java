package com.example.springbootmicroservicesframework.dto;

import com.example.springbootmicroservicesframework.enums.SortDirection;
import com.example.springbootmicroservicesframework.utils.Const;
import com.example.springbootmicroservicesframework.validation.ValidEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppPageRequest {

    @Min(value = Const.LONG_ZERO)
    Integer pageNumber;

    @Min(value = Const.LONG_ZERO)
    @Max(value = Const.MAXIMUM_PAGE_SIZE)
    Integer pageSize;

    String sortColumn;

    @ValidEnum(enumClass = SortDirection.class)
    String sortDirection;
}
