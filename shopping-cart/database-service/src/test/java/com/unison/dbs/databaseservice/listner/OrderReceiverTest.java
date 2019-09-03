package com.unison.dbs.databaseservice.listner;

import com.unison.dbs.databaseservice.converter.JsonMessageConverter;
import com.unison.dbs.databaseservice.entity.OrderEntity;
import com.unison.dbs.databaseservice.service.OrderManagementServiceImpl;
import org.apache.activemq.junit.EmbeddedActiveMQBroker;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.*;
import javax.persistence.PersistenceException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderReceiverTest {

    @ClassRule
    public static EmbeddedActiveMQBroker broker = new EmbeddedActiveMQBroker();

    @Mock
    private JmsTemplate jmsTemplate;

    @Mock
    TextMessage textMessage;

    @Autowired
    OrderReceiver orderReceiver;

    @Mock
    OrderManagementServiceImpl orderManagementService;

    @Mock
    JsonMessageConverter jsonMessageConverter;

    @Mock
    MapMessage mapMessage;
    @Mock
    private Queue errorQueue;
    @Test
    public void onMessageTest() throws JMSException {
        String message = "{\n" +
                "\t\"msgId\" : \"1234\",\n" +
                "\t\"order\" : {\n" +
                "\t\t\"name\" : \"def\",\n" +
                "\t\t\"price\" : 110.5,\n" +
                "\t\t\"quantity\" : 2\n" +
                "\t}\n" +
                "}";
        when(textMessage.getText()).thenReturn(message);
        orderReceiver.onMessage(textMessage);
    }

    @Test(expected = Exception.class)
    public void onMessageErrorTest() throws Exception {
        String message = "{\n" +
                "\t\"msgId\" : \"1234\",\n" +
                "\t\"order\" : {\n" +
                "\t\t\"name\" : \"def\",\n" +
                "\t\t\"price\" : 110.5,\n" +
                "\t\t\"quantity\" : 2\n" +
                "\t}\n" +
                "}";
        when(textMessage.getText()).thenReturn(message);
        doThrow(new JMSException("Error")).when(textMessage).acknowledge();
        doNothing().when(jmsTemplate).convertAndSend(any(Queue.class),any(Message.class));
        when(orderManagementService.process(any(OrderEntity.class))).thenThrow(new PersistenceException("Error"));
        orderReceiver.onMessage(textMessage);
    }

    @Test(expected = Exception.class)
    public void onMessageConvertErrorTest() throws Exception {
        String message = "{\n" +
                "\t\"msgId\" : \"1234\",\n" +
                "\t\"order\" : {\n" +
                "\t\t\"name\" : \"def\",\n";
        when(textMessage.getText()).thenReturn(message);
        when(jsonMessageConverter.fromMessage(any(Message.class))).thenThrow(new MessageConversionException("Convert Error"));
        orderReceiver.onMessage(textMessage);
    }

    @Test
    public void onMessageProcessErrorTest() throws Exception {
        String message = "{\n" +
                "\t\"msgId\" : \"1234\",\n" +
                "\t\"order\" : {\n" +
                "\t\t\"name\" : \"def\",\n" +
                "\t\t\"price\" : 110.5,\n" +
                "\t\t\"quantity\" : 2\n" +
                "\t}\n" +
                "}";
        when(textMessage.getText()).thenReturn(message);
        doThrow(new PersistenceException()).when(orderManagementService).process(any());
        doThrow(new PersistenceException("Error")).when(textMessage).acknowledge();
        orderReceiver.onMessage(textMessage);
    }

    @Test(expected = Exception.class)
    public void onMessageTypeErrorTest() throws Exception {
        doNothing().when(jmsTemplate).convertAndSend(any(Destination.class),any(Message.class));
        orderReceiver.onMessage(mapMessage);
    }

}