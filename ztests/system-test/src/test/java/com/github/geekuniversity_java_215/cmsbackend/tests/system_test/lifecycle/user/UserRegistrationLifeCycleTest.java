package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.lifecycle.user;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar.ConfirmRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.oauth.OauthTestRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar.RegistrarRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UnconfirmedUserDto;
import com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@Slf4j
public class UserRegistrationLifeCycleTest {

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

    @Test
    void newUserLifeCycle() {

        // 1. Register new user

        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.NEW_USER);

        UnconfirmedUserDto newUserDto = new UnconfirmedUserDto(defaultProperties.getLogin().getUsername(),
            defaultProperties.getLogin().getPassword(),"Пользователь","Новый",
            "cmsbackendgeek@gmail.com","932494356678");

        // Use here anonymous login
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ANONYMOUS);

        log.info("1. Registering new user: {}", defaultProperties);
        ResponseEntity<String> registrarResponse = registrarRequest.registrate(newUserDto);

        String confirmToken = registrarResponse.getBody();

        Assert.assertFalse("confirmToken is empty",  StringUtils.isBlank(confirmToken));

        log.info("2. Got confirmation token: {}", confirmToken);

        // 2. Confirm new user
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.ANONYMOUS);

        ResponseEntity<Void> confirmResponse = confirmRequest.confirm(confirmToken);
        log.info("3. Confirm new user result: {}", confirmResponse.getStatusCode());

        // 3. Perform requests by the user
        userConfig.switchJrpcClientProperties(SystemTestSpringConfiguration.NEW_USER);
        ResponseEntity<String> oauthTestResponse = oauthTestRequest.test();
        Assert.assertEquals("HttpStatus.status not 200", HttpStatus.OK, oauthTestResponse.getStatusCode());
        Assert.assertEquals("response body not expected", "SERVLET CONTAINER GRRREET YOU!",
            oauthTestResponse.getBody());

        log.info("4. Called auth-server.oauth.test:");
        log.info(oauthTestResponse.getBody());
    }
}
