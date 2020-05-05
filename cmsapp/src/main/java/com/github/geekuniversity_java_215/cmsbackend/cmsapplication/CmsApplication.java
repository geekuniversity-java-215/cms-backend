package com.github.geekuniversity_java_215.cmsbackend.cmsapplication;


import com.github.geekuniversity_java_215.cmsbackend.core.configurations.MultimoduleSpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MultimoduleSpringBootApplication
// Intellij Idea scanning Spring Configuration fix
// Bugfigz for IDE complaint: "Could not autowire" but really do nothing, only remove warning
// https://stackoverflow.com/questions/26889970/intellij-incorrectly-saying-no-beans-of-type-found-for-autowired-repository#comment92967484_31891805
@SpringBootApplication(scanBasePackages ="com.github.geekuniversity_java_215.cmsbackend")
public class CmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CmsApplication.class, args);
	}
}