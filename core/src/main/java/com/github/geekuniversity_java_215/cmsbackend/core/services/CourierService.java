package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CourierRepository;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import org.springframework.stereotype.Service;

@Service
public class CourierService extends BaseRepoAccessService<Courier> {

    private final CourierRepository courierRepository;

    public CourierService(CourierRepository courierRepository) {
        super(courierRepository);
        this.courierRepository = courierRepository;
    }
}