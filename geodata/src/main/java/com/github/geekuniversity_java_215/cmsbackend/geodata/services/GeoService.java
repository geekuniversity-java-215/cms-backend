package com.github.geekuniversity_java_215.cmsbackend.geodata.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.Transport;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.dreamworkerln.spring.utils.rest.RestTemplateFactory;

@Service
@Slf4j
public class GeoService {

    // Это вынести в настройки resource/geodata.properties
    private static final String ROUTE_URL = "http://router.project-osrm.org/route/v1/";

    // https://nominatim.org/release-docs/develop/api/Search/
    // Это вынести в настройки resource/geodata.properties
    private static final String CODE_URL = "https://nominatim.openstreetmap.org/search?q=100";

    private final RestTemplate restTemplate = RestTemplateFactory.getRestTemplate(10000);

    private final ObjectMapper mapper;

    @Autowired
    public GeoService(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    public String getRoute(Order order) throws JsonProcessingException {

        String fromPoint = restTemplate.getForObject(getGeocodeUrl(order.getFrom()), String.class);
        String toPoint = restTemplate.getForObject(getGeocodeUrl(order.getTo()), String.class);

        String[] fromPoints = fromPoint.split("},");
        String[] toPoints = toPoint.split("},");
        String primaryPointFrom = fromPoints[0].substring(1) + "}";
        String primaryPointTo = toPoints[0].substring(1) + "}";

        JsonNode nodeFrom = null, nodeTo = null;

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

        String route = getRouteUrl(order, Transport.DRIVING);
        return restTemplate.getForObject(route, String.class);
    }

    private static String getRouteUrl(Order order, Transport transport){

        StringBuilder url = new StringBuilder();
        url.append(ROUTE_URL).append(transport).append("/");
        url.append(order.getFrom().getLongitude()).append(",").append(order.getFrom().getLatitude()).append(";");
        url.append(order.getTo().getLongitude()).append(",").append(order.getTo().getLatitude());
        return url.toString();
    }

    private static String getGeocodeUrl(Address address){
        return CODE_URL + address.addressFormatToRequest() + "&format=json";
    }

}
