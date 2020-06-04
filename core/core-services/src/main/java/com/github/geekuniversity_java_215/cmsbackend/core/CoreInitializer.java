package com.github.geekuniversity_java_215.cmsbackend.core;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
@Order(-40000)
@Slf4j
public class CoreInitializer implements ApplicationRunner {

    private final UserRoleService userRoleService;

    private final Environment environment;



    public CoreInitializer(UserRoleService userRoleService, Environment environment) {
        this.userRoleService = userRoleService;
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String port = environment.getProperty("local.server.port");
        if (!StringUtils.isEmpty(port)) {
            log.info("Embedded Tomcat run at port: {}", port);
        }

        log.debug("CoreInitializer started");
        initDataBase();
        log.debug("CoreInitializer finished");
    }


    private void initDataBase() {

        // Merge UserRole.Values to database
        log.debug("Update database");

        log.debug("Merge roles");
        List<UserRole> roleList = userRoleService.findAll(null);
        Map<String, UserRole> roleMap =
                roleList.stream().collect(Collectors.toMap(UserRole::getName, Function.identity()));

        List<UserRole> toUpdate = new ArrayList<>();

        for (String name : UserRole.ROLE_NAMES) {
            if (!roleMap.containsKey(name)) {
                toUpdate.add(new UserRole(name));
            }
        }
        userRoleService.saveAll(toUpdate);
        log.debug("Database updated");
    }
}
