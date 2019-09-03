package com.unison.dbs.databaseservice.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unison.dbs.databaseservice.dto.OrderMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class JsonMessageConverter implements MessageConverter {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        String json;
        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
            json = mapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new MessageConversionException("Message cannot be parsed. ", e);
        }
        TextMessage message = session.createTextMessage();
        message.setText(json);
        return message;
    }

    @Override
    public OrderMessage fromMessage(Message message) throws MessageConversionException {
        OrderMessage orderMessage = null;
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        try {
            orderMessage = mapper.readValue(((TextMessage) message).getText(), OrderMessage.class);
        } catch (Exception e) {
            throw new MessageConversionException("Message cannot be parsed. ", e);
        }
        return orderMessage;
    }
}

