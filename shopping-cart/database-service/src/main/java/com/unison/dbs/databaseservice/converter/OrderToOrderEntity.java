package com.unison.dbs.databaseservice.converter;

import com.unison.dbs.databaseservice.dto.Order;
import com.unison.dbs.databaseservice.entity.OrderEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderToOrderEntity {

    public Order fromOrderEntity(OrderEntity orderEntity){
        Order order = new Order();
        order.setName(orderEntity.getName());
        order.setPrice(orderEntity.getPrice());
        order.setQuantity(orderEntity.getQuantity());
        return order;
    }

    public OrderEntity toOrderEntity(Order order){
        OrderEntity orderEntity  = new OrderEntity();
        orderEntity.setName(order.getName());
        orderEntity.setPrice(order.getPrice());
        orderEntity.setQuantity(order.getQuantity());
        return orderEntity;
    }
}