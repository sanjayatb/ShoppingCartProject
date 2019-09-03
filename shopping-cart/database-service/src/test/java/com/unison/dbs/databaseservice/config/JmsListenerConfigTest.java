package com.unison.dbs.databaseservice.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import javax.jms.Queue;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = JmsListenerConfig.class)
public class JmsListenerConfigTest {

    @Autowired
    JmsListenerConfig jmsListenerConfig;

    @Value("${error.queue}")
    private String errorQueueName;

    @Test
    public void errorQueue() throws JMSException {
        Queue queue = jmsListenerConfig.errorQueue();
        assertEquals(errorQueueName,queue.getQueueName());
    }
}