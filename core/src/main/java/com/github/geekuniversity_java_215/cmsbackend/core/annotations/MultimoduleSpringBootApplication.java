package com.github.geekuniversity_java_215.cmsbackend.core.annotations;

import com.github.geekuniversity_java_215.cmsbackend.core.repository.base.RepositoryWithEntityManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited

@SpringBootApplication(scanBasePackages = "com.github.geekuniversity_java_215.cmsbackend")
@EnableJpaRepositories(basePackages = "com.github.geekuniversity_java_215.cmsbackend",
                       repositoryBaseClass = RepositoryWithEntityManager.class)
@EntityScan(basePackages = {"com.github.geekuniversity_java_215.cmsbackend"})
public @interface MultimoduleSpringBootApplication {
}
