package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.ClientRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class ClientService extends BaseRepoAccessService<Client> {

    private final ClientRepository clientRepository;
    private final UserService userService;

    public ClientService(ClientRepository clientRepository, UserService userService) {
        super(clientRepository);
        this.clientRepository = clientRepository;
        this.userService = userService;
    }

    public Optional<Client> findOneByUser(User user) {
        return clientRepository.findOneByUser(user);
    }

    public Optional<Client> findByUsername(String username) {
        return clientRepository.findOneByUserUsername(username);
    }


    public Client getCurrent() {

        AtomicReference<Client> result = new AtomicReference<>();
        findOneByUser(userService.getCurrent()).ifPresent(result::set);
        return result.get();
    }

    // =================================================================================================



}
