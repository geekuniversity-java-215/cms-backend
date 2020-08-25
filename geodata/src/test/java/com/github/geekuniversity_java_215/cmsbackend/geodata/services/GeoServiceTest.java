package com.github.geekuniversity_java_215.cmsbackend.geodata.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.utils.Junit5Extension;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith({Junit5Extension.class})
@Slf4j
class GeoServiceTest {

    @Autowired
    private GeoService geoService;

    @Disabled
    @Test
    void getRoute() {

        Address from = new Address("Санкт-Петербург", "Кондратьевский", 70, 2, 0);
        Address to = new Address("Москва", "Порядковый переулок", 21, 0, 0);

        Order order = new Order();
        order.setFrom(from);
        order.setTo(to);

        // Если не упало по CONNECTION_REFUSED или 404 то уже хорошо.
        double distance = geoService.getRoute(order);


        //ToDo: доделать валидацию теста
        log.info("{}", distance);
    }
}
