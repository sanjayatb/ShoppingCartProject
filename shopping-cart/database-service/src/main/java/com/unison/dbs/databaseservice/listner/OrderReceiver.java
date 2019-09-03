package com.unison.dbs.databaseservice.listner;

import com.unison.dbs.databaseservice.converter.JsonMessageConverter;
import com.unison.dbs.databaseservice.converter.OrderToOrderEntity;
import com.unison.dbs.databaseservice.dto.Order;
import com.unison.dbs.databaseservice.dto.OrderMessage;
import com.unison.dbs.databaseservice.entity.OrderEntity;
import com.unison.dbs.databaseservice.service.OrderManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.persistence.PersistenceException;

@Component
public class OrderReceiver implements MessageListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderReceiver.class);

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    JsonMessageConverter messageConverter;

    @Autowired
    private Queue errorQueue;

    @Autowired
    private OrderManagementService orderManagementService;

    @Autowired
    private OrderToOrderEntity orderToOrderEntity;

    @JmsListener(destination = "${order.queue}", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                OrderMessage orderMessage = messageConverter.fromMessage(message);
                LOGGER.info("Received message='{}'", orderMessage.getMsgID());
                Order order = orderMessage.getOrder();
                OrderEntity orderEntity = orderToOrderEntity.toOrderEntity(order);
                LOGGER.info("Received Order='{}'", orderEntity.getName());
                try {
                    orderManagementService.process(orderEntity);
                    message.acknowledge();
                }catch (PersistenceException e){
                    LOGGER.error("Fail to Connect to database", e);
                } catch (Exception e) {
                    LOGGER.error("Fail to save in database", e);
                    jmsTemplate.convertAndSend(errorQueue,message);
                }
            } catch (MessageConversionException e) {
                LOGGER.error("unable to read message payload", e);
                jmsTemplate.convertAndSend(errorQueue,message);
            }
        } else {
            LOGGER.error("received unsupported message type");
            jmsTemplate.convertAndSend(errorQueue,message);
        }
    }
}
