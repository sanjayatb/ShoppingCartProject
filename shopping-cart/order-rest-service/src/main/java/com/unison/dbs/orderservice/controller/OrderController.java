package com.unison.dbs.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import javax.jms.Message;
import javax.jms.Queue;

@RestController
@RequestMapping("/publish")
public class OrderController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @GetMapping(path="/{order}/{id}", produces = "application/json")
    public String publish(@PathVariable("order") final String order,
                          @PathVariable("id") final String id) {
        String message = "{\n" +
                "\t\"msgId\" : \""+id+"\",\n" +
                "\t\"order\" : {\n" +
                "\t\t\"name\" : \""+order+"\",\n" +
                "\t\t\"price\" : 110.5,\n" +
                "\t\t\"quantity\" : 2\n" +
                "\t}\n" +
                "}";
        jmsTemplate.convertAndSend(queue, message);
        return message;
    }

}
