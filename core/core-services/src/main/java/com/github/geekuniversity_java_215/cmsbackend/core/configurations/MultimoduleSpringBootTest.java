package com.github.geekuniversity_java_215.cmsbackend.core.configurations;

import com.github.geekuniversity_java_215.cmsbackend.utils.repositories.RepositoryWithEntityManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited


@Configuration
@EnableAutoConfiguration
@ComponentScan({ "com.github.geekuniversity_java_215.cmsbackend"})


@EnableJpaRepositories(basePackages = "com.github.geekuniversity_java_215.cmsbackend",
                       repositoryBaseClass = RepositoryWithEntityManager.class)
@EntityScan(basePackages = {"com.github.geekuniversity_java_215.cmsbackend"})
public @interface MultimoduleSpringBootTest {
}
