package com.github.geekuniversity_java_215.cmsbackend.chat;


import com.github.geekuniversity_java_215.cmsbackend.core.configuration.MultimoduleSpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MultimoduleSpringBootApplication
// Intellij Idea scanning Spring Configuration fix
@SpringBootApplication(scanBasePackages ="com.github.geekuniversity_java_215.cmsbackend")
public class ChatApplication {
	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}
}