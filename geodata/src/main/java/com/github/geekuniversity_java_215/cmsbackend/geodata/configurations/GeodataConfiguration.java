package com.github.geekuniversity_java_215.cmsbackend.geodata.configurations;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GeodataConfiguration {

    @Value("${geodata.route.url}")
    private String routeUrl;


    @Value("${geodata.reverse-geocode.url}")
    private String reverseGeocodeUrl;

    @Value("${geodata.transport.type}")
    private String TransportType;
}
