package com.github.geekuniversity_java_215.cmsbackend.cmsapplication;

import com.github.geekuniversity_java_215.cmsbackend.core.annotations.MultimoduleSpringBootApplication;

import org.springframework.boot.SpringApplication;


@MultimoduleSpringBootApplication
public class CmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CmsApplication.class, args);
	}
}