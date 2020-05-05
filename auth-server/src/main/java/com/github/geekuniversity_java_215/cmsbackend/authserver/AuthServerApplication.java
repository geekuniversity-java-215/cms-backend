package com.github.geekuniversity_java_215.cmsbackend.authserver;


import com.github.geekuniversity_java_215.cmsbackend.core.configuration.MultimoduleSpringBootApplication;
import org.springframework.boot.SpringApplication;


@MultimoduleSpringBootApplication
//@SpringBootApplication(scanBasePackages ="com.github.geekuniversity_java_215.cmsbackend") // legacy IDE support
public class AuthServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
}