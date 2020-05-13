package com.github.geekuniversity_java_215.cmsbackend.geodata.services;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.geodata.configurations.GeodataConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.dreamworkerln.spring.utils.rest.RestTemplateFactory;


@Service
@Slf4j
public class GeoService {

    private final RestTemplate restTemplate = RestTemplateFactory.getRestTemplate(10000);
    private final GeodataConfiguration geoConf;

    @Autowired
    public GeoService(GeodataConfiguration geoConf) {
        this.geoConf = geoConf;
    }


    public double getRoute(Order order) {

        String fromPoint = restTemplate.getForObject(getGeocodeUrl(order.getFrom()), String.class);
        String toPoint = restTemplate.getForObject(getGeocodeUrl(order.getTo()), String.class);

        //FixMe
        Assert.isTrue(fromPoint != null, "fromPoint == null");
        Assert.isTrue(toPoint != null, "fromPoint == null");

        JSONArray jsonArrayFrom = new JSONArray(fromPoint);
        String fromLongitude = jsonArrayFrom.getJSONObject(0).getString("lon");
        String fromLatitude = jsonArrayFrom.getJSONObject(0).getString("lat");

        JSONArray jsonArrayTo = new JSONArray(toPoint);
        String toLongitude = jsonArrayTo.getJSONObject(0).getString("lon");
        String toLatitude = jsonArrayTo.getJSONObject(0).getString("lat");

        order.getFrom().setLongitude(fromLongitude);
        order.getFrom().setLatitude(fromLatitude);
        order.getTo().setLongitude(toLongitude);
        order.getTo().setLatitude(toLatitude);

        String route = getRouteUrl(order);
        double distance = 0;
        try {
            route = restTemplate.getForObject(route, String.class);
            JSONObject jsonRoute = new JSONObject(route);
            JSONArray jsonArrayRoute = jsonRoute.getJSONArray("routes");
            distance = jsonArrayRoute.getJSONObject(0).getDouble("distance");
        } catch (HttpClientErrorException.NotFound e) {
            log.error("route not found");
        }
        return distance;
    }

    // ==================================================================

    private String getRouteUrl(Order order) {

        StringBuilder url = new StringBuilder();
        url.append(geoConf.getRouteUrl()).append(geoConf.getTransportType()).append("/");
        url.append(order.getFrom().getLongitude()).append(",").append(order.getFrom().getLatitude()).append(";");
        url.append(order.getTo().getLongitude()).append(",").append(order.getTo().getLatitude());
        return url.toString();
    }

    private String getGeocodeUrl(Address address) {
        return geoConf.getReverseGeocodeUrl() + address.addressFormatToRequest() + "&format=json";
    }

}
