package com.github.geekuniversity_java_215.cmsbackend.core;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.UserRole;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserRoleService;


@Component
@Order(-1000)
@Slf4j
public class CoreEntitiesInitializer implements ApplicationRunner {

    private final UserRoleService userRoleService;

    public CoreEntitiesInitializer(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Merge UserRole.Values to database

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
    }
}
