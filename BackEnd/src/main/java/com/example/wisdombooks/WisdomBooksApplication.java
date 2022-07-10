package com.example.wisdombooks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@ServletComponentScan
@SpringBootApplication
public class WisdomBooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(WisdomBooksApplication.class, args);
    }

}
