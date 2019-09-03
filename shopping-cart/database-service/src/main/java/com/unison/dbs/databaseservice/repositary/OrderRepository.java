package com.unison.dbs.databaseservice.repositary;

import com.unison.dbs.databaseservice.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity save(OrderEntity entity);
//    OrderEntity findByName(String name);
}
