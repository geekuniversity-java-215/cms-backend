package com.github.geekuniversity_java_215.cmsbackend.core;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UsersInitializer {

    private final UserService userService;
    private final UserRoleService userRoleService;

    @Autowired
    public UsersInitializer(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void initUsers() {

        User user;
        log.info("initialize default users");

        // admin user  --------------------------------------------------
        // root/toor
        if (!userService.findByUsername("root").isPresent()) {
            user = new User("root",
                "{bcrypt}$2y$10$yvFUsJ1pZJd7WNrJ/A8hCO47Z1cNBHfMiduq4yioaEzuM1.QfSTUa",
                "root", "root", "root@mail.ru", "root");
            user.getRoles().add(userRoleService.findByName(UserRole.ADMIN).get());
            user.getRoles().add(userRoleService.findByName(UserRole.MANAGER).get());
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
            user = new User("user", "{bcrypt}$2y$10$oH1fincRPhJw7UomJl4N1eNtW4A7Ms5U92jnUhZ7MWQkCsVk61ovC",
                "user", "user", "user@mail.ru", "user");
            userService.save(user);
        }

        // vasya/vasya_password
        if (!userService.findByUsername("vasya").isPresent()) {
            user = new User("vasya", "{bcrypt}$2y$10$bgbAXDst.gXsocsw1E0u8ucW6YcmFvCb60OhYHtn01mkJsoEVwv2u",
                "Вася", "Пупкин", "vasya@mail.ru", "1122334455");
            userService.save(user);
        }

        // sema/sema_password
        if (!userService.findByUsername("sema").isPresent()) {
            user = new User("sema", "{bcrypt}$2y$10$0F1BgtfQlWeQbTL7CUWT0OzSBw95Ct20x3.INZG7CSSGTJoV5zQpy",
                "Сема", "Пасечкин", "sema@mail.ru", "908796786543");
            userService.save(user);
        }

        log.info("initialize default users done");
    }
}
