package com.github.geekuniversity_java_215.cmsbackend.authserver;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthServerInitializer implements ApplicationRunner {

    private final UserService userService;
    private final UserRoleService userRoleService;

    public AuthServerInitializer(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("AuthServerInitializer started");
        initUsers();
        log.debug("AuthServerInitializer finished");
    }


    private void initUsers() {

        User user = new User("Вася", "Пупкин", "vasya@mail.ru", "1122334455");
        user.setLogin("user");
        user.setPassword("{bcrypt}$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6");//password
        user.getRoles().add(userRoleService.findByName(UserRole.USER));
        userService.save(user);

        user = new User("Сема", "Пасечкин", "sema@mail.ru", "908796786543");
        user.setLogin("admin");
        user.setPassword("{bcrypt}$2y$10$3UKKfqyHoDe8MbVIkXr.UO8d76bJWisYP5DdC3EpSzro.JYzi38xu");//password
        user.getRoles().add(userRoleService.findByName(UserRole.ADMIN));
        userService.save(user);
    }
}
