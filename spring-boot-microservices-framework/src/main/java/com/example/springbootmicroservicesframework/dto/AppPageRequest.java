package com.example.springbootmicroservicesframework.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class AppPageRequest {

    Integer pageNumber;

    Integer pageSize;

    Set<String> sortColumn;

    String sortDirection;


}
