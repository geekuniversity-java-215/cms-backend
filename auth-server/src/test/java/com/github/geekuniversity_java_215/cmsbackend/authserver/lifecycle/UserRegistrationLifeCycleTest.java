package com.github.geekuniversity_java_215.cmsbackend.authserver.lifecycle;

import com.github.geekuniversity_java_215.cmsbackend.authserver.AuthServerApplication;
import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.AuthServerTestSpringConfiguration;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.UnconfirmedUserService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.confirm.ConfirmRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar.RegistrarRequest;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.user.UnconfirmedUserDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.UnrecoverableEntryException;

@SpringBootTest(classes = AuthServerApplication.class)
@Slf4j
public class UserRegistrationLifeCycleTest {

    @Autowired
    AuthServerTestSpringConfiguration authServerTestSpringConfiguration;
    @Autowired
    private RegistrarRequest registrarRequest;
    @Autowired
    private ConfirmRequest confirmRequest;
    @Autowired
    private UnconfirmedUserService unconfirmedUserService;
    @Autowired
    private UserService UserService;

    @Test
    void newUserLifeCycle() {

        // 1. Register new user

        // Use here admin login
        authServerTestSpringConfiguration.switchJrpcClientProperties(AuthServerTestSpringConfiguration.REGISTRAR);

        UnconfirmedUserDto newUserDto = new UnconfirmedUserDto("newuser",
            "newuser_password", "Пользователь", "Новый", "newuser@mail.ru", "932494356678");

        ResponseEntity<String> registrarResponse = registrarRequest.add(newUserDto);

        String confirmToken = registrarResponse.getBody();

        Assert.assertFalse("confirmToken is empty",  StringUtils.isBlank(confirmToken));

        log.info("confirmToken: {}", confirmToken);


        UnconfirmedUser newUser = unconfirmedUserService.findByUsername("newuser")
            .orElseThrow(() -> new RuntimeException("New UnconfirmedUser not persisted"));

        Assert.assertEquals("Returned wrong UnconfirmedUser", "newuser", newUser.getUsername());


        // 2. Confirm new user
        authServerTestSpringConfiguration.switchJrpcClientProperties(AuthServerTestSpringConfiguration.ANONYMOUS);

        ResponseEntity<Void> confirmResponse = confirmRequest.confirm(confirmToken);

        //ToDo: enable this

//        User user = UserService.findByUsername("newuser")
//            .orElseThrow(() -> new RuntimeException("New UnconfirmedUser not persisted"));
//
//        Assert.assertEquals("Returned wrong User", "newuser", user.getUsername());

    }
}
