package com.unison.dbs.databaseservice.service;

import com.unison.dbs.databaseservice.entity.OrderEntity;
import com.unison.dbs.databaseservice.repositary.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)

public class OrderManagementServiceImplTest {

    @MockBean
    private OrderRepository repository;

    @Autowired
    private OrderManagementService orderManagementService;

    @TestConfiguration
    static class OrderManagementServiceImplTestContextConfiguration {
        @Bean
        public OrderManagementService orderManagementService() {
            return new OrderManagementServiceImpl();
        }
    }

    @Test
    public void shouldProcessAndSaveValidMessage() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        when(repository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        orderManagementService.process(orderEntity);
        verify(repository, times(1)).save(any(OrderEntity.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void shouldSaveValidOrderEntity() throws Exception {
        OrderEntity orderEntity = new OrderEntity();
        when(repository.save(any(OrderEntity.class))).thenReturn(orderEntity);
        OrderEntity saveItemEntity = null;
        saveItemEntity = orderManagementService.process(orderEntity);
        assertEquals(orderEntity.getName(), saveItemEntity.getName());
        assertEquals(orderEntity.getPrice(), saveItemEntity.getPrice(), 10);
        assertEquals(orderEntity.getQuantity(), saveItemEntity.getQuantity(), 2);
        verify(repository, times(1)).save(any(OrderEntity.class));
    }

    @Test(expected = Exception.class)
    public void errorInSaveTest() throws Exception {
        when(repository.save(any(OrderEntity.class))).thenThrow(new SQLException("Save fail"));
        OrderEntity orderEntity = new OrderEntity();
        OrderEntity saveItemEntity = orderManagementService.process(orderEntity);
    }
}