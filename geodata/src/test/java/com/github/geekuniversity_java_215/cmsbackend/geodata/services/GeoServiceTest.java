package com.github.geekuniversity_java_215.cmsbackend.geodata.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class GeoServiceTest {

    @Autowired
    private GeoService geoService;


    @Disabled // Походу osrm в дауне
    @Test
    void getRoute() throws JsonProcessingException {

        Address from = new Address("Санкт-Петербург", "Кондратьевский", 70, 2, 0);
        Address to = new Address("Москва", "Красная площадь", 1, 0, 0);

        Order order = new Order();
        order.setFrom(from);
        order.setTo(to);

        String result = geoService.getRoute(order);
        log.info(result);
    }
}