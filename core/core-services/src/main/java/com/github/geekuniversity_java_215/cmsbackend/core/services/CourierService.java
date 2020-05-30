package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Client;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CourierRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CourierService extends BaseRepoAccessService<Courier> {

    private final CourierRepository courierRepository;

    public CourierService(CourierRepository courierRepository) {
        super(courierRepository);
        this.courierRepository = courierRepository;
    }

    public Optional<Courier> findOneByUser(User user) {
        return courierRepository.findOneByUser(user);
    }

    public Optional<Courier> findByUsername(String username) {
        return courierRepository.findOneByUserUsername(username);
    }

}