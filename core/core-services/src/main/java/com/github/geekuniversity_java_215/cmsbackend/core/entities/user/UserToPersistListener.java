package com.github.geekuniversity_java_215.cmsbackend.core.entities.user;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.HashSet;

@Component
@Slf4j
public class UserToPersistListener {

    static private UserRoleService userRoleService;
    static private AccountService accountService;

    @Autowired
    public void init(UserRoleService userRoleService, AccountService accountService) {
        UserToPersistListener.userRoleService = userRoleService;
        UserToPersistListener.accountService = accountService;
    }

    @PrePersist
    @PreUpdate
    public void methodExecuteBeforeSave(User user) {

        // Make sure that User contains persisted UserRoles
        for (UserRole role : new HashSet<>(user.getRoles())) {

            if (role.getId() == null) {
                user.getRoles().remove(role);
                user.getRoles().add(userRoleService.findByName(role.getName())
                    .orElseThrow(() -> new IllegalArgumentException("UserRole " + role.getName() + "not found")));
            }
        }

        // создадим для User аккаунт, если его у него нет
        if (user.getAccount() == null) {
            accountService.findByUser(user)
                .ifPresent(account -> {
                    throw new IllegalArgumentException("User " + user.getUsername() + " already have Account");
                });
        }
        Account account = new Account();
        user.setAccount(account);
        account.setUser(user);
        accountService.save(account);
    }
}
