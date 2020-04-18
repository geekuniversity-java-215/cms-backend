package com.github.geekuniversity_java_215.cmsbackend.service;

import com.github.geekuniversity_java_215.cmsbackend.entites.Order;
import com.github.geekuniversity_java_215.cmsbackend.repository.OrderRepository;
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
public class OrderService {


    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAllById(List<Long> listId) {

        return orderRepository.findAllById(listId);
    }

    public List<Order> findAll(Specification<Order> spec) {
        return orderRepository.findAll(spec);
    }

    public Order save(Order order) {


        order = orderRepository.save(order);

        return order;
    }

    public void delete(Order order) {

        orderRepository.delete(order);
    }

    public Page<Order> findAll(Specification<Order> spec, PageRequest pageable) {

        return orderRepository.findAll(spec, pageable);
    }
}
