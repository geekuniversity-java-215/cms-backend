package com.github.geekuniversity_java_215.cmsbackend.core;

import com.github.geekuniversity_java_215.cmsbackend.core.annotations.MultimoduleSpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Tests need spring configuration
 */
@MultimoduleSpringBootApplication
@SpringBootApplication(scanBasePackages ="com.github.geekuniversity_java_215.cmsbackend")
public class SpringBootTestApplication {
}
