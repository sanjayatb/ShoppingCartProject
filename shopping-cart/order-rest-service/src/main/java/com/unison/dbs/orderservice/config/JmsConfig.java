package com.unison.dbs.orderservice.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Queue;
import javax.jms.Session;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${order.queue}")
    private String queueName;

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String userName;

    @Value("${spring.activemq.password}")
    private String password;

    @Bean
    public Queue orderQueue() {
        return new ActiveMQQueue(queueName);
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setPassword(userName);
        activeMQConnectionFactory.setUserName(password);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return jmsTemplate;
    }

}
