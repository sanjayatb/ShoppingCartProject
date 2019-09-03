package com.unison.dbs.databaseservice.entity;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class OrderEntityTest {

    private OrderEntity orderEntity;

    @Before
    public void setUp() throws Exception {
        orderEntity = new OrderEntity();
    }

    @Test
    public void getAndSetIdTest() {
        orderEntity.setId(10);
        assertEquals(10,orderEntity.getId());
    }

    @Test
    public void getAndSetNameTest() {
        orderEntity.setName("abc");
        assertEquals("abc",orderEntity.getName());
    }

    @Test
    public void getAndSetPriceRTest() {
        orderEntity.setPrice(102.22233);
        assertEquals(102.22233,orderEntity.getPrice(),3);
    }

    @Test
    public void getAndSetQuantityTest() {
        orderEntity.setQuantity(10);
        assertEquals(10,orderEntity.getQuantity(),1);
    }

}