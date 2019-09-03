package com.unison.dbs.databaseservice.dto;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    private Order order;

    @Before
    public void setUp() throws Exception {
        order = new Order();
    }

    @Test
    public void getAndSetName() {
        order.setName("test");
        assertEquals("test",order.getName());
    }

    @Test
    public void getAndSetPrice() {
        order.setPrice(100.2093);
        assertEquals(100.2093,order.getPrice(),2);
    }

    @Test
    public void getAndSetQuantity() {
        order.setQuantity(100);
        assertEquals(100,order.getQuantity(),2);
    }
}