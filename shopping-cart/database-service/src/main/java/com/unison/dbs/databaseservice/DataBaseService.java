package com.unison.dbs.databaseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableJms
@EnableRetry
public class DataBaseService {

    public static void main(String[] args) {
        SpringApplication.run(DataBaseService.class, args);
    }
}
