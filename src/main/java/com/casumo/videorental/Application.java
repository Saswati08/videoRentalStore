package com.casumo.videorental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(GlobalExceptionHandler.class)
@ComponentScan(basePackages = "com.casumo.videorental")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
