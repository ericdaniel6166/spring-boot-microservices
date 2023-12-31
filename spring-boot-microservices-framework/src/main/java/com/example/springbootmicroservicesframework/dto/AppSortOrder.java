package com.example.springbootmicroservicesframework.dto;

import com.example.springbootmicroservicesframework.utils.Const;
import com.example.springbootmicroservicesframework.validation.ValidEnumString;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppSortOrder {

    @NotBlank
    String sortColumn = Const.ID;

    @NotBlank
    @ValidEnumString(value = Sort.Direction.class, caseSensitive = false)
    String sortDirection = Sort.Direction.ASC.name();

    public Sort.Order mapToSortOrder() {
        return new Sort.Order(Sort.Direction.fromString(sortDirection), sortColumn);

    }
}
