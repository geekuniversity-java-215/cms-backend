package com.github.geekuniversity_java_215.cmsbackend.services;

import com.github.geekuniversity_java_215.cmsbackend.entites.Order;
import com.github.geekuniversity_java_215.cmsbackend.repository.OrderRepository;
import com.github.geekuniversity_java_215.cmsbackend.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OrderService extends BaseService<Order> {


    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
    }
}
