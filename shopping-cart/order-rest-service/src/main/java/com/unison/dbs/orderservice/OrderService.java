package com.unison.dbs.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class OrderService {
    public static void main(String[] args) {
        SpringApplication.run(OrderService.class,args);
    }

}
