package geo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Order;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.Transport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Controller
public class GeoController {

    @Autowired
    private Construct construct;

    @PostMapping("/")
    public String getRoute(@ModelAttribute(name = "order") Order order) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        String fromPoint = restTemplate.getForObject(construct.decodeAddressToPoint(order.getFrom()), String.class);
        String toPoint = restTemplate.getForObject(construct.decodeAddressToPoint(order.getTo()), String.class);
        String[] fromPoints = fromPoint.split("},");
        String[] toPoints = toPoint.split("},");
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

        order.getFrom().setLongitude(lonFrom);
        order.getFrom().setLatitude(latFrom);
        order.getTo().setLongitude(lonTo);
        order.getTo().setLatitude(latTo);

        String route = construct.createRouteByPoint(order, Transport.driving);
        return restTemplate.getForObject(route, String.class);
    }
}