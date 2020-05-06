package com.github.geekuniversity_java_215.cmsbackend.authserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(-1000)
@Slf4j
public class AuthServerInitializer implements ApplicationRunner {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ObjectMapper objectMapper;

    private static boolean loaded = false;

    public AuthServerInitializer(UserService userService, UserRoleService userRoleService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Из-за того, что в тестах запускается auth-server целиком,
        // все ApplicationRunner вызываются по второму кругу
        // kludge-o-matic
        if (loaded) {
            return;
        }
        loaded = true;

        log.debug("AuthServerInitializer started");
        initUsers();
        log.debug("AuthServerInitializer finished");


    }


    private void initUsers() {

        // admin user  --------------------------------------------------
        // root/toor
        User user = new User("root",
            "{bcrypt}$2y$10$yvFUsJ1pZJd7WNrJ/A8hCO47Z1cNBHfMiduq4yioaEzuM1.QfSTUa",
            "root", "root", "root@mail.ru", "root");
        user.getRoles().add(userRoleService.findByName(UserRole.ADMIN));
        userService.save(user);

        // frontend user that register new users  ---------------------
        // registrar/registrar
        user = new User("registrar",
                "{bcrypt}$2y$10$C5kaSyYpioNZN8oL4NkWbOJiEG0JscafiQycLxQCfD8F6y/tjxtSm",
                "Registrar", "Registrar", "registrar@mail.ru", "registrar");
        user.getRoles().add(userRoleService.findByName(UserRole.REGISTRAR));
        userService.save(user);
        //log.info(objectMapper.valueToTree(user).toPrettyString());

        // demo users --------------------------------------------------

        // vasya/password
        user = new User("vasya", "{bcrypt}$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
                "Вася", "Пупкин", "vasya@mail.ru", "1122334455");
        user.getRoles().add(userRoleService.findByName(UserRole.USER));
        userService.save(user);

        // sema/password
        user = new User("sema","{bcrypt}$2y$10$3UKKfqyHoDe8MbVIkXr.UO8d76bJWisYP5DdC3EpSzro.JYzi38xu",
                "Сема", "Пасечкин", "sema@mail.ru", "908796786543");
        user.getRoles().add(userRoleService.findByName(UserRole.USER));
        userService.save(user);
    }
}
