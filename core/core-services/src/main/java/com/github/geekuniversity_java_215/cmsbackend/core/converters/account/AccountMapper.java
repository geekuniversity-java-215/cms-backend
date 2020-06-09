package com.github.geekuniversity_java_215.cmsbackend.core.converters.account;

import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.AbstractMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters._base.InstantMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.converters.user.UserMapper;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Account;
import com.github.geekuniversity_java_215.cmsbackend.core.services.AccountService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserRoleService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.Account.AccountDto;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.ERROR,
    uses = {InstantMapper.class})
public abstract class AccountMapper extends AbstractMapper<Account, AccountDto> {

    @Autowired
    private AccountService accountService;


    @PostConstruct
    private void postConstruct() {
        this.baseRepoAccessService = accountService;
        constructor = new AccountMapper.EntityConstructor();
    }

    @Mapping(target = "user", ignore = true)
    public abstract AccountDto toDto(Account account);

    @Mapping(target = "user", ignore = true)
    public abstract Account toEntity(AccountDto accountDto);

    @AfterMapping
    Account afterMapping(AccountDto source, @MappingTarget Account target) {
        return merge(source, target);
    }

    protected class EntityConstructor extends Constructor<Account, AccountDto> {

        //private UserRoleService userRoleService;

        @Override
        public Account create(AccountDto dto, Account entity) {
            return new Account();
        }

    }


}
