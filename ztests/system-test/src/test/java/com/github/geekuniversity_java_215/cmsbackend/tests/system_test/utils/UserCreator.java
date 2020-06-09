package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.client.ClientRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.courier.CourierRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.manager.ClientManagerRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.manager.CourierManagerRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.manager.UserManagerRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.user.UserRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.client.ClientDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.courier.CourierDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;
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

    @Autowired
    private UserManagerRequest userManagerRequest;
    @Autowired
    private ClientManagerRequest clientManagerRequest;
    @Autowired
    private CourierManagerRequest courierManagerRequest;


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
        user.getRoles().add(UserRole.USER);

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);

        UserDto userDto = userManagerRequest.findByUsername(user.getUsername());
        if (userDto == null) {
            userManagerRequest.save(user);
        }
        // update user (get persisted id)
        user = userManagerRequest.findByUsername(user.getUsername());

        if (clientManagerRequest.findByUser(user) == null) {
            ClientDto client = new ClientDto(user, "Client-Client-Client");
            clientManagerRequest.save(client);
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
        user.getRoles().add(UserRole.USER);

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);

        UserDto userExists = userManagerRequest.findByUsername(user.getUsername());
        if (userExists == null) {
            userManagerRequest.save(user);
        }
        // update user (get persisted id)
        user = userManagerRequest.findByUsername(user.getUsername());

        if (courierManagerRequest.findByUser(user) == null) {
            CourierDto courier = new CourierDto(user, "Courier-Courier-Courier");
            courierManagerRequest.save(courier);
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
