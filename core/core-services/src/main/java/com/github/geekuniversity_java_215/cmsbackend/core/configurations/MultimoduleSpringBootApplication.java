package com.github.geekuniversity_java_215.cmsbackend.core.configurations;

import com.github.geekuniversity_java_215.cmsbackend.core.repositories.RepositoryWithEntityManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

@SpringBootApplication(scanBasePackages = "com.github.geekuniversity_java_215.cmsbackend")
@EnableJpaRepositories(basePackages = "com.github.geekuniversity_java_215.cmsbackend",
                       repositoryBaseClass = RepositoryWithEntityManager.class)
@EntityScan(basePackages = {"com.github.geekuniversity_java_215.cmsbackend"})
@EnableGlobalMethodSecurity(
    prePostEnabled = true,
    securedEnabled = true,
    jsr250Enabled = true)
public @interface MultimoduleSpringBootApplication {
}
