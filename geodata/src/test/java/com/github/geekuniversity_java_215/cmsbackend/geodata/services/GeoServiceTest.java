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


    @Test
    void getRoute() throws JsonProcessingException {

        Address from = new Address("Санкт-Петербург", "Кондратьевский", 70, 2, 0);
        Address to = new Address("Москва", "Порядковый переулок", 21, 0, 0);

        Order order = new Order();
        order.setFrom(from);
        order.setTo(to);

        // Если не упало по CONNECTION_REFUSED или 404 то уже хорошо.
        String result = geoService.getRoute(order);

        //ToDo: доделать валидацию теста
        log.info(result);
    }
}
