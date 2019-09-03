package com.unison.dbs.databaseservice.service;

import com.unison.dbs.databaseservice.entity.OrderEntity;

/**
 * @author sanjaya
 */

public interface OrderManagementService {

//    @Retryable(value = { ConnectException.class }, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    OrderEntity process(OrderEntity orderEntity) throws Exception;

//    @Recover
//    String getBackendResponseFallback(RuntimeException e);
}
