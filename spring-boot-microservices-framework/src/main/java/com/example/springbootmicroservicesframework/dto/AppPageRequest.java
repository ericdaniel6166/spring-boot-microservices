package com.example.springbootmicroservicesframework.dto;

import com.example.springbootmicroservicesframework.utils.Const;
import com.example.springbootmicroservicesframework.validation.ValidEnum;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppPageRequest {

    @Min(value = Const.ONE)
    Integer pageNumber = Const.ONE;

    @Min(value = Const.DEFAULT_PAGE_SIZE)
    @Max(value = Const.MAXIMUM_PAGE_SIZE)
    Integer pageSize = Const.DEFAULT_PAGE_SIZE;

    //    @Size(max = 2) //delete //for local test
    @Size(max = Const.MAXIMUM_SORT_COLUMN)
    List<String> sortColumn = Collections.singletonList(Const.ID);

    @ValidEnum(enumClass = Sort.Direction.class, caseSensitive = false)
    String sortDirection = Sort.Direction.ASC.name();
}
