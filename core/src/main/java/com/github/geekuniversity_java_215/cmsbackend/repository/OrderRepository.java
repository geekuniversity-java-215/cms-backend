package com.github.geekuniversity_java_215.cmsbackend.repository;

import com.github.geekuniversity_java_215.cmsbackend.entites.Order;
import com.github.geekuniversity_java_215.cmsbackend.repository.base.CustomRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import java.util.List;

public interface OrderRepository extends CustomRepository<Order, Long>, JpaSpecificationExecutor<Order> {


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("FROM Order o " +
           "WHERE o = :#{#order}")
    List<Order> lockByOrder(@Param("order")Order order);
}
