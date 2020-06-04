package com.github.geekuniversity_java_215.cmsbackend.core.converters;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.order.OrderConverter;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AddressService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.order.OrderService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;


import javax.annotation.PostConstruct;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@SpringBootTest
@Slf4j
class ConverterTests {

    @Autowired
    private OrderConverter orderConverter;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressService addressService;



    //Заместо @BeforeAll
    @PostConstruct
    private void postConstruct() {
        log.info("Checking Converter an Mapper logic");
    }


    @Test
    void OrderConverterTest() {

        log.info("Checking OrderConverter");

        Order order = orderService.findById(1L).get();

        JsonNode orderJson = orderConverter.toDtoJson(order);
        String json = orderJson.toString();
        log.info(orderJson.toPrettyString());
        Order newWorldOrder = orderConverter.toEntity(orderJson);

//        copyTimes(newWorldOrder, order);
//        copyTimes(newWorldOrder.getFrom(), order.getFrom());
//        copyTimes(newWorldOrder.getTo(), order.getTo());

        JsonNode orderJsonNew = orderConverter.toDtoJson(order);
        String jsonNew = orderJson.toString();
        log.info(orderJsonNew.toPrettyString());

        Assert.isTrue(json.equals(jsonNew), "OrderConverter failed");
    }



    private void copyTimes(AbstractEntity entity, AbstractEntity from) {

        Utils.fieldSetter("created", entity, from.getCreated());
        Utils.fieldSetter("updated", entity, from.getUpdated());

    }
}