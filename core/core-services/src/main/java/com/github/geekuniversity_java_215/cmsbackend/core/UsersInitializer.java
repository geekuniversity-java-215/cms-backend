package com.github.geekuniversity_java_215.cmsbackend.core;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UsersInitializer {

    private final UserService userService;

    @Autowired
    public UsersInitializer(UserService userService) {
        this.userService = userService;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void initUsers() {

        User user;
        log.info("initialize default users");

        // admin user  --------------------------------------------------
        // root/toor
        if (!userService.findByUsername("root").isPresent()) {
            user = new User("root",
                "{bcrypt}$2a$10$SQUaDnIckmdPr1Wf/WOYiOL42yn0zCPHoM9qC3XNYsH9NyLqVbWKK",
                "root", "root", "root@mail.ru", "root");
            user.getRoles().add(UserRole.ADMIN);
            user.getRoles().add(UserRole.MANAGER);
            userService.save(user);
        }

//        // frontend user that register new users  ---------------------
//        // registrar/registrar_password
//        if (!userService.findByUsername("registrar").isPresent()) {
//            user = new User("registrar",
//                "{bcrypt}$2y$10$hEAtx1Hu3cBCb46umeGvUeH1PmyJI4pxDRTsUixto67JSH5W4VI4W",
//                "Registrar", "Registrar", "registrar@mail.ru", "registrar");
//            user.getRoles().add(userRoleService.findByName(UserRole.REGISTRAR));
//            userService.save(user);
//        }

        // demo users --------------------------------------------------

        // user/user_password
        if (!userService.findByUsername("user").isPresent()) {
            user = new User("user", "{bcrypt}$2a$10$4oxoJzJti6NZmP.4d98VJue..tPH6otFLlgMTuA.nWHvtmnZRupE2",
                "user", "user", "user@mail.ru", "user");
            userService.save(user);
        }

        // vasya/vasya_password
        if (!userService.findByUsername("vasya").isPresent()) {
            user = new User("vasya", "{bcrypt}$2a$10$ptWulW3vFICm8Pu.CmulbuNx1GsgwO8UHrcZuVJi22mF792qRxjMu",
                "Вася", "Пупкин", "vasya@mail.ru", "1122334455");
            userService.save(user);
        }

        // sema/sema_password
        if (!userService.findByUsername("sema").isPresent()) {
            user = new User("sema", "{bcrypt}$2a$10$zqdgSPaIehsb82r7psbBKOU5bkfCo8pqv9BwuwLz5BoEcSXQuqdnW",
                "Сема", "Пасечкин", "sema@mail.ru", "908796786543");
            userService.save(user);
        }

        log.info("initialize default users done");
    }
}
