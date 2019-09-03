package com.unison.dbs.databaseservice.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import javax.jms.Queue;
import javax.jms.Session;

import static org.apache.activemq.ActiveMQSession.INDIVIDUAL_ACKNOWLEDGE;

@EnableJms
@Configuration
public class JmsListenerConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.activemq.user}")
    private String user;

    @Value("${spring.activemq.password}")
    private String password;

    @Value("${error.queue}")
    private String errorQueueName;

    @Bean
    public Queue errorQueue() {
        return new ActiveMQQueue(errorQueueName);
    }

    @Bean
    public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        activeMQConnectionFactory.setUserName(user);
        activeMQConnectionFactory.setPassword(password);
        return activeMQConnectionFactory;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(receiverActiveMQConnectionFactory());
        factory.setSessionAcknowledgeMode(INDIVIDUAL_ACKNOWLEDGE);
        return factory;
    }
    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(receiverActiveMQConnectionFactory());
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        return jmsTemplate;
    }

//    @Bean
//    public DefaultMessageListenerContainer orderMessageListenerContainer() {
//        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
//        endpoint.setMessageListener(new OrderReceiver());
//        endpoint.setDestination("order-queue");
//        return jmsListenerContainerFactory().createListenerContainer(endpoint);
//    }

//    @Bean
//    public DefaultMessageHandlerMethodFactory handlerMethodFactory() {
//        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
//        factory.setMessageConverter(messageConverter());
//        return factory;
//    }
//
    @Bean
    public MessageConverter messageConverter() {
        return new MappingJackson2MessageConverter();
    }
//
//    @Override
//    public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
//        registrar.setMessageHandlerMethodFactory(handlerMethodFactory());
//    }
}
