package com.example.springbootmicroservicesframework.dto;

import com.example.springbootmicroservicesframework.validation.ValidEnum;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppSortOrder {

    @NotBlank
    String sortColumn;

    @ValidEnum(enumClass = Sort.Direction.class, caseSensitive = false)
    String sortDirection = Sort.Direction.ASC.name();

    public Sort.Order mapToSortOrder() {
        return new Sort.Order(Sort.Direction.fromString(sortDirection), sortColumn);

    }
}
