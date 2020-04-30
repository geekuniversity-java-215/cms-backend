package com.github.geekuniversity_java_215.cmsbackend.geodata.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.Transport;
import org.springframework.stereotype.Service;

@Service
public class GeoService {

    // Это вынести в настройки resource/geodata.properties
    private static final String ROUTE_URL = "http://router.project-osrm.org/route/v1/";

    // q=100 это что ?
    // https://nominatim.org/release-docs/develop/api/Search/

    // Это вынести в настройки resource/geodata.properties
    //private static final String CODE_URL = "https://nominatim.openstreetmap.org/search?q=100";
    private static final String CODE_URL = "https://nominatim.openstreetmap.org/search?";

    public String createRoute(Order order, Transport transport){

        StringBuilder url = new StringBuilder();
        url.append(ROUTE_URL).append(transport).append("/");
        url.append(order.getFrom().getLongitude()).append(",").append(order.getFrom().getLatitude()).append(";");
        url.append(order.getTo().getLongitude()).append(",").append(order.getTo().getLatitude());
        return url.toString();
    }

    public String geocode(Address address){
        return CODE_URL +address.addressFormatToRequest()+"&format=json";
    }
}
