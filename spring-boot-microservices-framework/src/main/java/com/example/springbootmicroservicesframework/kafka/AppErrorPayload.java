//package com.example.springbootmicroservicesframework.kafka;
//
//import com.example.springbootmicroservicesframework.exception.AppException;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.FieldDefaults;
//
//import java.io.Serializable;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class AppErrorPayload implements Serializable {
//
//    static final String APP_ERROR = "APP_ERROR";
//
//    String errorCode;
//    String errorMessage;
//
//    public AppErrorPayload from(AppException appException) {
//        return AppErrorPayload.builder()
//                .errorCode(appException.getErrorCode())
//                .errorMessage(appException.getMessage())
//                .build();
//    }
//
//    public AppErrorPayload from(Exception exception) {
//        return AppErrorPayload.builder()
//                .errorCode(APP_ERROR)
//                .errorMessage(exception.getMessage())
//                .build();
//    }
//
//}
