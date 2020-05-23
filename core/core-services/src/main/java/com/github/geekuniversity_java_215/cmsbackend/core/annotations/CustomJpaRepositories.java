package com.github.geekuniversity_java_215.cmsbackend.core.annotations;

import com.github.geekuniversity_java_215.cmsbackend.core.repositories.RepositoryWithEntityManager;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableJpaRepositories(basePackages = "com.github.geekuniversity_java_215.cmsbackend.core.repository",
                       repositoryBaseClass = RepositoryWithEntityManager.class)
@EntityScan(basePackages = {"com.github.geekuniversity_java_215.cmsbackend.core.entities"})
public @interface CustomJpaRepositories {
}
