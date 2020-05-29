package com.github.geekuniversity_java_215.cmsbackend.cmsapplication;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.*;
import com.github.geekuniversity_java_215.cmsbackend.mail.services.MailService;
import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.entities.TestEntity;
import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.repositories.TestRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CmsInitializer implements ApplicationRunner {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ClientService clientService;
    private final CourierService courierService;
    private final TestRepository testRepository;
    private final MailService mailService;
    private final OrderService orderService;

    @Autowired
    public CmsInitializer(UserService userService,
                          UserRoleService userRoleService, ClientService clientService,
                          CourierService courierService,
                          TestRepository testRepository,
                          MailService mailService,
                          OrderService orderService) {

        this.userService = userService;
        this.userRoleService = userRoleService;
        this.clientService = clientService;
        this.courierService = courierService;
        this.testRepository = testRepository;
        this.mailService = mailService;
        this.orderService = orderService;
    }

    @Override
    public void run(ApplicationArguments args) {

        //log.info("CmsAppDemoInitializer - add basic entities to DB");

        testRepository.save(new TestEntity("Вася test"));

        // Only for Tests for H2 database
        initUsers();
    }


    private void initUsers() {

        User user;

        log.info("initialize default users");

        // admin user  --------------------------------------------------
        // root/toor
        if (!userService.findByUsername("root").isPresent()) {
            user = new User("root",
                "{bcrypt}$2y$10$yvFUsJ1pZJd7WNrJ/A8hCO47Z1cNBHfMiduq4yioaEzuM1.QfSTUa",
                "root", "root", "root@mail.ru", "root");
            user.getRoles().add(userRoleService.findByName(UserRole.ADMIN));
            user.getRoles().add(userRoleService.findByName(UserRole.MANAGER));
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
            user.getRoles().add(userRoleService.findByName(UserRole.USER));
            userService.save(user);
        }




        // vasya/vasya_password
        if (!userService.findByUsername("vasya").isPresent()) {
            user = new User("vasya", "{bcrypt}$2y$10$bgbAXDst.gXsocsw1E0u8ucW6YcmFvCb60OhYHtn01mkJsoEVwv2u",
                "Вася", "Пупкин", "vasya@mail.ru", "1122334455");
            user.getRoles().add(userRoleService.findByName(UserRole.USER));
            userService.save(user);
        }

        // sema/sema_password
        if (!userService.findByUsername("sema").isPresent()) {
            user = new User("sema", "{bcrypt}$2y$10$0F1BgtfQlWeQbTL7CUWT0OzSBw95Ct20x3.INZG7CSSGTJoV5zQpy",
                "Сема", "Пасечкин", "sema@mail.ru", "908796786543");
            user.getRoles().add(userRoleService.findByName(UserRole.USER));
            userService.save(user);
        }
    }
}

