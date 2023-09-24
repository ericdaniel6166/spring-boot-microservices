package com.example.springbootmicroservicesframework.kafka;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Event implements Serializable {

    private static final long serialVersionUID = 37397013697L;

    Object payload;
}
