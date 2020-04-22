package com.github.geekuniversity_java_215.cmsbackend;

import com.github.geekuniversity_java_215.cmsbackend.repository.base.RepositoryWithEntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = RepositoryWithEntityManager.class)
public class CmsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsBackendApplication.class, args);
	}

}
