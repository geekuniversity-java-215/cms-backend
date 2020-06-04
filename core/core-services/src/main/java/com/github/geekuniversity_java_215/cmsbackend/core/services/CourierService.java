package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CourierRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.services.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class CourierService extends BaseRepoAccessService<Courier> {

    private final CourierRepository courierRepository;
    private final UserService userService;

    public CourierService(CourierRepository courierRepository, UserService userService) {
        super(courierRepository);
        this.courierRepository = courierRepository;
        this.userService = userService;
    }

    public Optional<Courier> findOneByUser(User user) {
        return courierRepository.findOneByUser(user);
    }

    public Optional<Courier> findByUsername(String username) {
        return courierRepository.findOneByUserUsername(username);
    }


    public Courier getCurrent() {

        AtomicReference<Courier> result = new AtomicReference<>();
        findOneByUser(userService.getCurrent()).ifPresent(result::set);
        return result.get();
    }

}