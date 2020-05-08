package com.github.geekuniversity_java_215.cmsbackend.geodata.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.geodata.configurations.GeodataConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.dreamworkerln.spring.utils.rest.RestTemplateFactory;

@Service
@Slf4j
public class GeoService {

    private final RestTemplate restTemplate = RestTemplateFactory.getRestTemplate(10000);
    private final GeodataConfiguration geoConf;
    private final ObjectMapper mapper;

    @Autowired
    public GeoService(GeodataConfiguration geoConf, ObjectMapper mapper) {
        this.geoConf = geoConf;
        this.mapper = mapper;
    }


    public String getRoute(Order order) throws JsonProcessingException {

        String fromPoint = restTemplate.getForObject(getGeocodeUrl(order.getFrom()), String.class);
        String toPoint = restTemplate.getForObject(getGeocodeUrl(order.getTo()), String.class);

        //FixMe
        Assert.isTrue(fromPoint != null, "fromPoint == null");
        Assert.isTrue(toPoint != null, "fromPoint == null");

        String[] fromPoints = fromPoint.split("},");
        String[] toPoints = toPoint.split("},");
        String primaryPointFrom = fromPoints[0].substring(1) + "}";
        String primaryPointTo = toPoints[0].substring(1) + "}";

        JsonNode nodeFrom, nodeTo;

        nodeFrom = mapper.readTree(primaryPointFrom);
        nodeTo = mapper.readTree(primaryPointTo);

        String latFrom = nodeFrom.get("lat").asText();
        String lonFrom = nodeFrom.get("lon").asText();
        String latTo = nodeTo.get("lat").asText();
        String lonTo = nodeTo.get("lon").asText();

        order.getFrom().setLongitude(lonFrom);
        order.getFrom().setLatitude(latFrom);
        order.getTo().setLongitude(lonTo);
        order.getTo().setLatitude(latTo);

        String route = getRouteUrl(order);
        return restTemplate.getForObject(route, String.class);
    }

    // ==================================================================

    private String getRouteUrl(Order order){

        StringBuilder url = new StringBuilder();
        url.append(geoConf.getRouteUrl()).append(geoConf.getTransportType()).append("/");
        url.append(order.getFrom().getLongitude()).append(",").append(order.getFrom().getLatitude()).append(";");
        url.append(order.getTo().getLongitude()).append(",").append(order.getTo().getLatitude());
        return url.toString();
    }

    private String getGeocodeUrl(Address address){
        return geoConf.getReverseGeocodeUrl() + address.addressFormatToRequest() + "&format=json";
    }

}
