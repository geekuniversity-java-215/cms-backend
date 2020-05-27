package com.github.geekuniversity_java_215.cmsbackend.tests.system_test.lifecycle.order;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.configurations.JrpcClientProperties;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar.ConfirmRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.oauth.OauthTestRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_client.request.registrar.RegistrarRequest;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.Account.AccountDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import com.github.geekuniversity_java_215.cmsbackend.tests.system_test.configurations.SystemTestSpringConfiguration;
import com.github.geekuniversity_java_215.cmsbackend.utils.SpringBeanUtilsEx;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

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

    @Test
    void newUserLifeCycle() throws InvocationTargetException, IllegalAccessException {

        UserDto user = new UserDto();
        user.setUsername("vasya");

        AccountDto account = new AccountDto();
        account.setBalance(new BigDecimal(3));
        account.setUser(user);
        account.setId(5L);

        user.setAccount(account);

        UserDto newUser = new UserDto();
        newUser.setEmail("mm@mail.ru");
        newUser.setId(1L);
        AccountDto newAccount = new AccountDto();
        newAccount.setUser(newUser);
        newUser.setAccount(newAccount);


        System.out.println("newUser: " + newUser);

        SpringBeanUtilsEx.CopyPropertiesExcludeNull(user, newUser);

        //BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);

        //BeanUtils.copyProperties(newUser, user);

        System.out.println("user: " + user);
        System.out.println("newUser: " + newUser);

        System.out.println("OK");


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
