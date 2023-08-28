package com.example.springbootmicroservicesframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example" })
public class SpringBootMicroservicesFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootMicroservicesFrameworkApplication.class, args);
    }

}
