package com.github.geekuniversity_java_215.cmsbackend.authserver;


import com.github.geekuniversity_java_215.cmsbackend.core.configurations.MultimoduleSpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MultimoduleSpringBootApplication
// Intellij Idea scanning Spring Configuration fix
@SpringBootApplication(scanBasePackages ="com.github.geekuniversity_java_215.cmsbackend")
public class AuthServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}
}