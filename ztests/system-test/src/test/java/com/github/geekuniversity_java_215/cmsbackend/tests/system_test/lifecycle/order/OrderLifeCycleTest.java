package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.lifecycle.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.client.ClientRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.courier.CourierRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.order.OrderClientRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.order.OrderCourierRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar.ConfirmRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.oauth.OauthTestRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar.RegistrarRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.user.UserRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.address.AddressDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.order.OrderDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
@Slf4j
public class OrderLifeCycleTest {

    @Autowired
    private SystemTestSpringConfiguration userConfig;
    @Autowired
    private RegistrarRequest registrarRequest;
    @Autowired
    private ConfirmRequest confirmRequest;
    @Autowired
    private OauthTestRequest oauthTestRequest;
    @Autowired
    private JrpcClientProperties defaultProperties;
    @Autowired
    private OrderClientRequest orderClientRequest;
    @Autowired
    private OrderCourierRequest orderCourierRequest;
    @Autowired
    private UserRequest userRequest;
    @Autowired
    private ClientRequest clientRequest;
    @Autowired
    private CourierRequest courierRequest;

    @Test
    void orderLifeCycle() throws JsonProcessingException {

        AddressDto from;
        AddressDto to;
        OrderDto order;
        Long orderId;
        UserDto user;

        // VASYA ----------------------------------------------------------------------------------

        // Up vasya to client and courier
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.VASYA);
        user = userRequest.getCurrent();
        Long clientId = userRequest.makeClient();
        log.info("clientId: {}", clientId);
        Long courierId = userRequest.makeCourier();
        log.info("courierId: {}", courierId);
        
        // CLIENT ----------------------------------------------------------------------------------
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.CLIENT);
        user = userRequest.getCurrent();

        user.setPayPalEmail("НаНаНАнАнАнАнАнА!!!!");
        userRequest.save(user);


        ClientDto client = clientRequest.getCurrent();





        // Prepare database here
        from = new AddressDto("Москва", "Улица красных тюленей", 1, 2, 3);
        to = new AddressDto("Мухосранск", "Западная", 2, 2, 5);

        // создаем заказ клиентом
        order = new OrderDto();
        order.setFrom(from);
        order.setTo(to);
        order.setClient(client);
        order.setStatus(OrderStatus.NEW);

        // сохраняем заказ
        long id = orderClientRequest.save(order);
        log.info("order id: {}", id);

        // смотрим на свой заказ
        List<OrderDto> orderList = orderClientRequest.findAll(null);
        log.info("orderz {}", orderList);

        // COURIER ----------------------------------------------------------------------------------

        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.COURIER);

        CourierDto courier = courierRequest.getCurrent();

        // получаем список новых заказов
        orderList = orderCourierRequest.findNew(null);
        log.info("new orderz {}", orderList);

        Assert.notEmpty(orderList, "orderList == null");

        order = orderList.get(0);
        Assert.isTrue(order.getStatus() == OrderStatus.NEW, "order.status != NEW");

        // берем заказ
        orderId = orderCourierRequest.accept(order.getId());
        order = orderCourierRequest.findById(orderId);
        Assert.isTrue(order.getStatus() == OrderStatus.ASSIGNED, "order.status != ASSIGNED");

        // выполняем заказ
        orderId = orderCourierRequest.execute(order.getId());
        order = orderCourierRequest.findById(orderId);
        Assert.isTrue(order.getStatus() == OrderStatus.TRANSIT, "order.status != IN_PROGRESS");

        // завершаем заказ
        orderId = orderCourierRequest.complete(order.getId());
        order = orderCourierRequest.findById(orderId);
        Assert.isTrue(order.getStatus() == OrderStatus.COMPLETED, "order.status != DONE");

//        UserDto user = new UserDto();
//        user.setUsername("vasya");
//
//        AccountDto account = new AccountDto();
//        account.setBalance(new BigDecimal(3));
//        account.setUser(user);
//        account.setId(5L);
//
//        user.setAccount(account);
//
//        UserDto newUser = new UserDto();
//        newUser.setEmail("mm@mail.ru");
//        newUser.setId(1L);
//        AccountDto newAccount = new AccountDto();
//        newAccount.setUser(newUser);
//        newUser.setAccount(newAccount);
//
//
//        System.out.println("newUser: " + newUser);
//
//        SpringBeanUtilsEx.CopyPropertiesExcludeNull(user, newUser);
//
//        //BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
//
//        //BeanUtils.copyProperties(newUser, user);
//
//        System.out.println("user: " + user);
//        System.out.println("newUser: " + newUser);
//
//        System.out.println("OK");


//        // 1. Register new user
//
//        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.NEW_USER);
//
//        UnconfirmedUserDto newUserDto = new UnconfirmedUserDto(defaultProperties.getLogin().getUsername(),
//            defaultProperties.getLogin().getPassword(),"Пользователь","Новый",
//            "cmsbackendgeek@gmail.com","932494356678");
//
//        // Use here anonymous login
//        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ANONYMOUS);
//
//        log.info("1. Registering new user: {}", defaultProperties);
//        ResponseEntity<String> registrarResponse = registrarRequest.registrate(newUserDto);
//
//        String confirmToken = registrarResponse.getBody();
//
//        Assert.assertFalse("confirmToken is empty",  StringUtils.isBlank(confirmToken));
//
//        log.info("2. Got confirmation token: {}", confirmToken);
//
//        // 2. Confirm new user
//        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ANONYMOUS);
//
//        ResponseEntity<Void> confirmResponse = confirmRequest.confirm(confirmToken);
//        log.info("3. Confirm new user result: {}", confirmResponse.getStatusCode());
//
//        // 3. Perform requests by the user
//        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.NEW_USER);
//        ResponseEntity<String> oauthTestResponse = oauthTestRequest.test();
//        Assert.assertEquals("HttpStatus.status not 200", HttpStatus.OK, oauthTestResponse.getStatusCode());
//        Assert.assertEquals("response body not expected", "SERVLET CONTAINER GRRREET YOU!",
//            oauthTestResponse.getBody());
//
//        log.info("4. Called auth-server.oauth.test:");
//        log.info(oauthTestResponse.getBody());
    }
}
