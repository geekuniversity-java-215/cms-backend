package com.github.geekuniversity_java_215.cmsbackend.core.repository;

import com.github.geekuniversity_java_215.cmsbackend.core.repository.base.CustomRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.util.List;

public interface OrderRepository extends CustomRepository<Order, Long> {

    // SELECT FOR UPDATE
    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("FROM Order o " +
           "WHERE o = :#{#order}")
    List<Order> lockByOrder(@Param("order")Order order);
}
