package com.unison.dbs.databaseservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class OrderMessage  {

    @JsonProperty(value = "msgId",required = true)
    @NotNull
    private String msgID;
    @JsonProperty("order")
    @NotNull
    private Order order;

    public String getMsgID() {
        return msgID;
    }

    public void setMsgID(String msgID) {
        this.msgID = msgID;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
