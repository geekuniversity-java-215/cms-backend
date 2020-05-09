package com.github.geekuniversity_java_215.cmsbackend.authserver.lifecycle;

import com.github.geekuniversity_java_215.cmsbackend.authserver.AuthServerApplication;
import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.AuthServerTestSpringConfiguration;
import com.github.geekuniversity_java_215.cmsbackend.authserver.service.UnconfirmedUserService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.confirm.ConfirmRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.oauth.OauthTestRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar.RegistrarRequest;
import com.github.geekuniversity_java_215.cmsbackend.protocol.dto.user.UnconfirmedUserDto;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = AuthServerApplication.class)
@Slf4j
public class UserRegistrationLifeCycleTest {

    @Autowired
    private AuthServerTestSpringConfiguration authServerTestSpringConfiguration;
    @Autowired
    private RegistrarRequest registrarRequest;
    @Autowired
    private ConfirmRequest confirmRequest;
    @Autowired
    private UnconfirmedUserService unconfirmedUserService;
    @Autowired
    private UserService UserService;
    @Autowired
    private OauthTestRequest oauthTestRequest;

    @Autowired
    private JrpcClientProperties defaultProperties;

    @Test
    void newUserLifeCycle() {

        // 1. Register new user

        authServerTestSpringConfiguration.switchJrpcClientProperties(AuthServerTestSpringConfiguration.NEW_USER);

        UnconfirmedUserDto newUserDto = new UnconfirmedUserDto(defaultProperties.getLogin().getUsername(),
            defaultProperties.getLogin().getPassword(),"Пользователь","Новый",
            "cmsbackendgeek@gmail.com","932494356678");

        // Use here registrar login
        authServerTestSpringConfiguration.switchJrpcClientProperties(AuthServerTestSpringConfiguration.REGISTRAR);

        ResponseEntity<String> registrarResponse = registrarRequest.registrate(newUserDto);

        String confirmToken = registrarResponse.getBody();

        Assert.assertFalse("confirmToken is empty",  StringUtils.isBlank(confirmToken));

        log.info("confirmToken: {}", confirmToken);


        UnconfirmedUser newUser = unconfirmedUserService.findByUsername("newuser")
            .orElseThrow(() -> new RuntimeException("New UnconfirmedUser not persisted"));

        Assert.assertEquals("Returned wrong UnconfirmedUser", "newuser", newUser.getUsername());

        // 2. Confirm new user
        authServerTestSpringConfiguration.switchJrpcClientProperties(AuthServerTestSpringConfiguration.ANONYMOUS);

        //ResponseEntity<Void> confirmResponse =
        confirmRequest.confirm(confirmToken);

        User user = UserService.findByUsername("newuser")
            .orElseThrow(() -> new RuntimeException("New UnconfirmedUser not persisted"));

        Assert.assertEquals("Returned wrong User", "newuser", user.getUsername());

        authServerTestSpringConfiguration.switchJrpcClientProperties(AuthServerTestSpringConfiguration.NEW_USER);

        ResponseEntity<String> oauthTestResponse = oauthTestRequest.test();
        log.info(oauthTestResponse.toString());
        Assert.assertEquals("HttpStatus.status not 200", HttpStatus.OK, oauthTestResponse.getStatusCode());
        Assert.assertEquals("response body not expected", "SERVLET CONTAINER GRRREET YOU!",
            oauthTestResponse.getBody());
    }
}
