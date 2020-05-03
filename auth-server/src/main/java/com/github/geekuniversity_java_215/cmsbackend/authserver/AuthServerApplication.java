package com.github.geekuniversity_java_215.cmsbackend.authserver;


import com.github.geekuniversity_java_215.cmsbackend.configuration.configurations.MultimoduleSpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MultimoduleSpringBootApplication
@SpringBootApplication(scanBasePackages ="com.github.geekuniversity_java_215.cmsbackend") // legacy IDE support
public class AuthServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
}