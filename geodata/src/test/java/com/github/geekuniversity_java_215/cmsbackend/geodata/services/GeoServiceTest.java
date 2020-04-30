package com.github.geekuniversity_java_215.cmsbackend.geodata.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GeoServiceTest {
    private String ROUTE_URL = "http://router.project-osrm.org/route/v1/";
    private String CODE_URL = "https://nominatim.openstreetmap.org/search?q=100";
    private String transport = "driving";
    Address address = new Address("Санкт-Петербург", "Кондратьевский", 20, 0, 100);
    String latFrom = "56.00222";
    String lonFrom = "25.56776";
    String latTo = "56.67222";
    String lonTo = "25.99776";

    @Test
    public void createRouteByPoint() {
        StringBuilder url = new StringBuilder();
        url.append(ROUTE_URL).append(transport).append("/");
        url.append(lonFrom).append(",").append(latFrom).append(";");
        url.append(lonTo).append(",").append(latTo);
        System.out.println(url);
    }

    @Test
    public void decodeAddressToPoint() {
        System.out.println(CODE_URL +address.addressFormatToRequest()+"&format=json");
    }
}