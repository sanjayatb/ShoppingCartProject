package com.unison.dbs.databaseservice.converter;

import com.unison.dbs.databaseservice.dto.Order;
import com.unison.dbs.databaseservice.dto.OrderMessage;
import com.unison.dbs.databaseservice.entity.OrderEntity;
import org.aspectj.weaver.ast.Or;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonMessageConverterTest {

    @Autowired
    private JsonMessageConverter jsonMessageConverter;

    @MockBean
    private Session session;

    @Mock
    TextMessage textMessage;

    @Test
    public void toMessage() throws JMSException {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setMsgID("msg123");
        Order order = new Order();
        order.setName("abc");
        orderMessage.setOrder(order);
        when(session.createTextMessage()).thenReturn(textMessage);
        Message message = jsonMessageConverter.toMessage(orderMessage,session);
        assertNotNull(message);
    }

    @Test(expected = MessageConversionException.class)
    public void toMessageError() throws JMSException {
        ClassThatJacksonCannotSerialize clazz = new ClassThatJacksonCannotSerialize();//Not a serializable class
        Message message = jsonMessageConverter.toMessage(clazz,session);
        assertNull(message);
    }

    @Test
    public void fromMessage() throws JMSException {
        String msgStr = "{\n" +
                "\t\"msgId\" : \"1234\",\n" +
                "\t\"order\" : {\n" +
                "\t\t\"name\" : \"def\",\n" +
                "\t\t\"price\" : 110.5,\n" +
                "\t\t\"quantity\" : 2\n" +
                "\t}\n" +
                "}";
        when(textMessage.getText()).thenReturn(msgStr);

        OrderMessage orderMessage = jsonMessageConverter.fromMessage(textMessage);
        assertNotNull(orderMessage);
        assertNotNull(orderMessage.getOrder());
        assertEquals("1234",orderMessage.getMsgID());
    }

    @Test(expected = MessageConversionException.class)
    public void fromMessageError() throws JMSException {
        String msgStr = "{\n" +
                "\t\"msgId\" : \"1234\",\n" +
                "\t\"order\" : {\n" +
                "\t\t\"";
        when(textMessage.getText()).thenReturn(msgStr);
        OrderMessage orderMessage = jsonMessageConverter.fromMessage(textMessage);
        assertNull(orderMessage);
    }
}
class ClassThatJacksonCannotSerialize {}