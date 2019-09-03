package com.unison.dbs.databaseservice.converter;

import com.unison.dbs.databaseservice.dto.Order;
import com.unison.dbs.databaseservice.entity.OrderEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderToOrderEntityTest {

    @Autowired
    private OrderToOrderEntity orderToOrderEntity;

    @Test
    public void fromOrderEntity() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(111);
        orderEntity.setName("test");
        orderEntity.setPrice(10);
        orderEntity.setQuantity(5);
        Order order = orderToOrderEntity.fromOrderEntity(orderEntity);
        assertNotNull(order);
        assertEquals(orderEntity.getName(),order.getName());
        assertEquals(orderEntity.getPrice(),order.getPrice(),10);
        assertEquals(orderEntity.getQuantity(),order.getQuantity(),10);
    }

    @Test
    public void toOrderEntity() {
        Order order = new Order();
        order.setName("test");
        order.setPrice(10);
        order.setQuantity(5);
        OrderEntity orderEntity = orderToOrderEntity.toOrderEntity(order);
        assertNotNull(orderEntity);
        assertEquals(order.getName(),orderEntity.getName());
        assertEquals(order.getPrice(),orderEntity.getPrice(),10);
        assertEquals(order.getQuantity(),orderEntity.getQuantity(),10);
    }
}