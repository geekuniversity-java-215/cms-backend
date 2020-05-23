package com.github.geekuniversity_java_215.cmsbackend.core.repositories;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.Courier;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends CustomRepository<Courier, Long> {
}
