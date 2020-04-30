package geo;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Address;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.Transport;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class Construct {

    private String ROUTE_URL = "http://router.project-osrm.org/route/v1/";
    private String CODE_URL = "https://nominatim.openstreetmap.org/search?q=100";

    public String createRouteByPoint(Order order, Transport transport){

        StringBuilder url = new StringBuilder();
        url.append(ROUTE_URL).append(transport).append("/");
        url.append(order.getFrom().getLongitude()).append(",").append(order.getFrom().getLatitude()).append(";");
        url.append(order.getTo().getLongitude()).append(",").append(order.getTo().getLatitude());
        return url.toString();
    }

    public String decodeAddressToPoint(Address address){
        return CODE_URL +address.addressFormatToRequest()+"&format=json";
    }
}
