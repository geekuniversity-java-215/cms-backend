package com.github.geekuniversity_java_215.cmsbackend.service;

import com.github.geekuniversity_java_215.cmsbackend.entites.Order;
import com.github.geekuniversity_java_215.cmsbackend.repository.OrderRepository;
import com.github.geekuniversity_java_215.cmsbackend.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;




@Service
@Transactional
public class OrderService extends BaseService<Order> {


    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
    }
}
