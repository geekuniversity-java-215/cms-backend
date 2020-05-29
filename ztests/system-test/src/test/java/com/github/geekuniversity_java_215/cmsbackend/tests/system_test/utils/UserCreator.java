package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar.ConfirmRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.user.UserRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UnconfirmedUserDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
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

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);

        UserDto userExists = userRequest.findByUsername(user.getUsername());
        if (userExists == null) {
            long id = userRequest.save(user);
            user = userRequest.findById(id);
            userRequest.makeClient(user);
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

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);

        UserDto userExists = userRequest.findByUsername(user.getUsername());
        if (userExists == null) {
            long id = userRequest.save(user);
            user = userRequest.findById(id);
            userRequest.save(user);
            userRequest.makeCourier(user);
        }
    }

    // -------------------------------------------------------------------------------

    public void createUser(UserDto user) throws JsonProcessingException {

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);
        userRequest.save(user);
    }

    public void makeUserClient(UserDto user) {

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);
        userRequest.makeClient(user);
    }

    public void makeUserCourier(UserDto user) {

        // Use here admin login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ADMIN);
        userRequest.makeCourier(user);
    }
    
}
