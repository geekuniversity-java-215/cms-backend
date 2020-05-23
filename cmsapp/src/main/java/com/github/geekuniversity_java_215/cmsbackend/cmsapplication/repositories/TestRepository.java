package com.github.geekuniversity_java_215.cmsbackend.cmsapplication.repositories;

import com.github.geekuniversity_java_215.cmsbackend.cmsapplication.entities.TestEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CustomRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends CustomRepository<TestEntity, Long> {
}
