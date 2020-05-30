package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.client.ClientRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.courier.CourierRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar.ConfirmRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.user.UserRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UnconfirmedUserDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserRoleDto;
import com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreator {

    @Autowired
    private SystemTestSpringConfiguration userConfig;
    @Autowired
    private UserRequest userRequest;
    @Autowired
    private ClientRequest clientRequest;
    @Autowired
    private CourierRequest courierRequest;
    @Autowired
    private JrpcClientProperties defaultProperties;


    public void createClientUser() throws JsonProcessingException {

        // Use here client login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.CLIENT);
        UserDto user = new UserDto();
        user.setUsername(defaultProperties.getLogin().getUsername());
        user.setPassword(defaultProperties.getLogin().getPassword());
        user.setEmail("client@mail.ru");
        user.setFirstName("Клиент");
        user.setLastName("Клиентович");
        user.setPhoneNumber("4358789567568");
        user.getRoles().add(UserRoleDto.getByName(UserRoleDto.USER));

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);

        UserDto userDto = userRequest.findByUsername(user.getUsername());
        if (userDto == null) {
            userRequest.save(user);
        }
        user = userRequest.findByUsername(user.getUsername());

        if (clientRequest.findByUser(user) == null) {
            ClientDto client = new ClientDto(user, "Client-Client-Client");
            clientRequest.save(client);
        }
    }



    public void createCourierUser() throws JsonProcessingException {

        // Use here courier login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.COURIER);
        UserDto user = new UserDto();
        user.setUsername(defaultProperties.getLogin().getUsername());
        user.setPassword(defaultProperties.getLogin().getPassword());
        user.setEmail("courier@mail.ru");
        user.setFirstName("Курьер");
        user.setLastName("Курьерович");
        user.setPhoneNumber("56767957549");
        user.getRoles().add(UserRoleDto.getByName(UserRoleDto.USER));

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);

        UserDto userExists = userRequest.findByUsername(user.getUsername());
        if (userExists == null) {
            userRequest.save(user);
        }
        user = userRequest.findByUsername(user.getUsername());

        if (courierRequest.findByUser(user) == null) {
            CourierDto courier = new CourierDto(user, "Courier-Courier-Courier");
            courierRequest.save(courier);
        }
    }

    // -------------------------------------------------------------------------------

    public void createUser(UserDto user) throws JsonProcessingException {

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);
        userRequest.save(user);
    }

    public void createClient(ClientDto client) {

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);
        clientRequest.save(client);
    }

    public void makeUserCourier(CourierDto courier) {

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);
        courierRequest.save(courier);
    }

}
