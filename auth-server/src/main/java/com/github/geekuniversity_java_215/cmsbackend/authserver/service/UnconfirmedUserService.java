package com.github.geekuniversity_java_215.cmsbackend.authserver.service;

import com.github.geekuniversity_java_215.cmsbackend.authserver.entities.UnconfirmedUser;
import com.github.geekuniversity_java_215.cmsbackend.authserver.repository.UnconfirmedUserRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class UnconfirmedUserService extends BaseRepoAccessService<UnconfirmedUser> {

    private final UnconfirmedUserRepository unconfirmedUserRepository;

    public UnconfirmedUserService(UnconfirmedUserRepository unconfirmedUserRepository) {
        super(unconfirmedUserRepository);
        this.unconfirmedUserRepository = unconfirmedUserRepository;
    }

    public Optional<UnconfirmedUser> findByLogin(String login) {
        Optional<UnconfirmedUser> result = Optional.empty();
        if (!StringUtils.isBlank(login)) {
            result = unconfirmedUserRepository.findOneByLogin(login);
        }
        return result;
    }
}
