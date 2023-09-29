package com.example.springbootmicroservicesframework.pagination;

import com.example.springbootmicroservicesframework.utils.Const;
import com.example.springbootmicroservicesframework.validation.ValidEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppPageRequest {

    @Min(value = Const.LONG_ONE)
    Integer pageNumber = Const.INTEGER_ONE;

    @Min(value = Const.DEFAULT_PAGE_SIZE)
    @Max(value = Const.MAXIMUM_PAGE_SIZE)
    Integer pageSize = Const.DEFAULT_PAGE_SIZE;

    @Size(max = Const.MAXIMUM_SORT_COLUMN)
//    @Size(max = 2) //delete //for local test
    String[] sortColumn = new String[]{Const.ID};

    @ValidEnum(enumClass = Sort.Direction.class, caseSensitive = false)
    String sortDirection = Sort.Direction.ASC.name();
}
