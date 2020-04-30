package com.github.geekuniversity_java_215.cmsbackend.geodata.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.Transport;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.geodata.services.GeoService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GeoControllerTest {


    @Test
    public void getRoute() {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        GeoService geoService = new GeoService();
        Order order = new Order();

        Address from = new Address("Санкт-Петербург", "Кондратьевский", 70, 2, 100);
        Address to = new Address("Санкт-Петербург", "Кондратьевский", 20, 0, 100);

        String fromPoint = restTemplate.getForObject(geoService.geocode(from), String.class);
        String toPoint = restTemplate.getForObject(geoService.geocode(to), String.class);

        String[] fromPoints = fromPoint.split("}");
        String[] toPoints = toPoint.split("}");
        String primaryPointFrom = fromPoints[0].substring(1) + "}";
        String primaryPointTo = toPoints[0].substring(1) + "}";
        JsonNode nodeFrom=null, nodeTo=null;
        try {
            nodeFrom = mapper.readTree(primaryPointFrom);
            nodeTo = mapper.readTree(primaryPointTo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String latFrom = nodeFrom.get("lat").asText();
        String lonFrom = nodeFrom.get("lon").asText();
        String latTo = nodeTo.get("lat").asText();
        String lonTo = nodeTo.get("lon").asText();

        from.setLongitude(lonFrom);
        from.setLatitude(latFrom);
        to.setLongitude(lonTo);
        to.setLatitude(latTo);

        order.setFrom(from);
        order.setTo(to);

        String route = geoService.createRoute(order, Transport.driving);
        System.out.println(restTemplate.getForObject(route, String.class));
    }