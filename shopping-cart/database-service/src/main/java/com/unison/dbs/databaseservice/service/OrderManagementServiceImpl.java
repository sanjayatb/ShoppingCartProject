package com.unison.dbs.databaseservice.service;

import com.unison.dbs.databaseservice.entity.OrderEntity;
import com.unison.dbs.databaseservice.repositary.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderManagementServiceImpl implements OrderManagementService {

    @Autowired
    private OrderRepository orderRepositary;

    @Override
    public OrderEntity process(OrderEntity orderEntity) throws Exception {
        OrderEntity savedEntity = orderRepositary.save(orderEntity);
        return savedEntity;
    }

//    @Override
//    public String getBackendResponseFallback(RuntimeException e) {
//        return "All retries completed, so Fallback method called";
//    }


}
